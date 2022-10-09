package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.IQuoteService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CryptocurrencyPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @Autowired
    IQuoteService quoteService;

    public Cryptocurrency getCryptocurrencyDB(){ return cryptocurrencyService.create("DAI");}
    public Cryptocurrency getCryptocurrencyDB2(){ return cryptocurrencyService.create("BITCOIN");}
    public Cryptocurrency getCryptocurrencyDB3(){ return cryptocurrencyService.create("LUNA");}
    public Cryptocurrency getCryptocurrencyDB4(){ return cryptocurrencyService.create("USDT");}

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewCryptocurrency() throws ResourceNotFound {
        Cryptocurrency saved = cryptocurrencyRepo.save(new Cryptocurrency("DAI"));
        int idSaved = saved.getId();
        Cryptocurrency finded = cryptocurrencyRepo.findById(idSaved).orElseThrow(() -> new ResourceNotFound
                ("nonexistent cryptocurrency"));

        assertEquals(finded.getId(), idSaved);
    }

    @Test
    void createACryptocurrencyCheckId() throws ResourceNotFound {
        int cryptocurrencyId = cryptocurrencyService.create("USDT").getId();

        assertEquals(cryptocurrencyId, cryptocurrencyService.findById(cryptocurrencyId).getId());
    }

    @Test
    void throwsResourceNotFoundWhenTryToGetADeleteACryptocurrency() {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        cryptocurrencyService.delete(cryptocurrencyId);

        assertThrows(ResourceNotFound.class, () -> cryptocurrencyService.findById(cryptocurrencyId));
    }

    @Test
    void updateACryptocurrencyCheckFieldModified() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        cryptocurrency.setName("LUNA");
        cryptocurrencyService.update(cryptocurrency);

        assertEquals("LUNA", cryptocurrencyService.findById(cryptocurrency.getId()).getName());
    }

    @Test
    void getAnEmptySetWhenAllCryptocurrenciesWasDeleted() {
        getCryptocurrencyDB();
        getCryptocurrencyDB2();
        getCryptocurrencyDB3();
        cryptocurrencyService.deleteAll();

        assertTrue(cryptocurrencyService.getAll().isEmpty());
    }

    @Test
    void getTheLatestQuoteFromCryptocurrency() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        int quote2Id = quoteService.create(cryptocurrency, dataSet.getSomePrice() + 2000).getId();

        assertEquals(quote2Id, cryptocurrencyService.getLatestQuote(cryptocurrencyService.findById(cryptocurrency.getId())).getId());
    }

    @Test
    void getTheLast24HoursQuotesFromCryptocurrency() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        Quote oldQuote = quoteService.create(cryptocurrency, dataSet.getSomePrice());
        oldQuote.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds());
        quoteService.update(oldQuote);

        assertEquals(4, cryptocurrencyService.last24hoursQuotes(cryptocurrencyService.findById(cryptocurrency.getId())).size());
    }

    @Test
    void get4CryptocurrenciesWhenAskForAllCryptocurrencies() {
        getCryptocurrencyDB();
        getCryptocurrencyDB2();
        getCryptocurrencyDB3();
        getCryptocurrencyDB4();

        assertEquals(4, cryptocurrencyService.getAll().size());
    }

    @Test
    void getQuotesFromACryptocurrency() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice());

        assertEquals(3, cryptocurrencyService.getQuotes(cryptocurrency).size());
    }

    @Test
    void getEmptyIntentionsFromACryptocurrencyWithoutIntentions() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();

        assertTrue(cryptocurrencyService.getReferencedIntentions(cryptocurrency).isEmpty());
    }
}
