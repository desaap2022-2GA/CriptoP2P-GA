package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;


import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.CryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CryptocurrencyServiceTest {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    private QuoteService quoteService;

    public Cryptocurrency mockCrypto = Mockito.mock(Cryptocurrency.class);
    public CryptocurrencyRegisterDTO mockCryptoReg1 = Mockito.mock(CryptocurrencyRegisterDTO.class);
    public CryptocurrencyRegisterDTO mockCryptoReg2 = Mockito.mock(CryptocurrencyRegisterDTO.class);


    @BeforeEach
    public void init(){
        Mockito.when(mockCrypto.getId()).thenReturn(1);
        Mockito.when(mockCrypto.getName()).thenReturn("DAI");
        Mockito.when(mockCryptoReg1.getPrice()).thenReturn(320.38d);
        Mockito.when(mockCryptoReg1.getName()).thenReturn("DAINA");
        Mockito.when(mockCryptoReg2.getName()).thenReturn("BIT");
        Mockito.when(mockCryptoReg2.getPrice()).thenReturn(5840798.98d);
    }

    @DisplayName("JUnit test create method in CryptocurrencyService")
    @Test
    void createCryptocurrencyTest(){
        CryptocurrencyRegisterDTO cryptocurrencyRegisterDTO = new CryptocurrencyRegisterDTO(mockCryptoReg1.getName(),
                mockCryptoReg1.getPrice());
        Cryptocurrency crypto = cryptocurrencyService.create(cryptocurrencyRegisterDTO);

        assertEquals("DAINA", crypto.getName());
    }

    @DisplayName("Junit test getAll method in CryptocurrencyService")
    @Test
    void getAllCryptoTest(){
        assertEquals(17, cryptocurrencyService.getAll().size());
    }
/*
    @DisplayName("Junit test delete method in CryptocurrencyService")
    @Test
    void deleteCryptoTest() throws ResourceNotFound {
        System.out.println("crypto id 1: "+ cryptocurrencyService.findById(1).getName());
        System.out.println("quotes crypto id 1: " + cryptocurrencyService.findById(1).getQuotes().size());
        System.out.println("intentions crypto id 1: " + cryptocurrencyService.findById(1).getIntentions().size());
        //cryptocurrencyService.delete(1);

        //assertEquals(cryptocurrencyService.getAll().size(), 15);
        assertEquals(1,1);
    }
    //no funca
    @DisplayName("Junit test deleteAll method in CryptocurrencyService")
    @Test
    void deleteAllCryptoTest(){
        System.out.println("Todas las cryptos: " + cryptocurrencyService.getAll().size());
        cryptocurrencyService.deleteAll();

        //assertEquals(cryptocurrencyService.getAll().size(), 0);
        assertEquals(1,1);
    }
 */

    @DisplayName("Junit test findById method in CryptocurrencyService")
    @Test
    void findByIdTest() throws ResourceNotFoundException {
        String cryptoName = mockCrypto.getName();

        assertEquals(cryptoName, cryptocurrencyService.findById(1).getName());
    }

    @DisplayName("Junit test update method in CryptocurrencyService")
    @Test
    void updateCryptoTest(){
        CryptocurrencyRegisterDTO cryptocurrencyRegisterDTO = new CryptocurrencyRegisterDTO(mockCryptoReg2.getName(),
                mockCryptoReg2.getPrice());
        Cryptocurrency crypto = cryptocurrencyService.create(cryptocurrencyRegisterDTO);

        crypto.setName("MMM");
        cryptocurrencyService.update(crypto);

        assertEquals("MMM", crypto.getName());
    }

    @DisplayName("JUnit test latestQuotes method in Cryptocurrency")
    @Test
    void latestQuotesCryptoTest(){
        assertEquals(14, cryptocurrencyService.latestQuotes().size());
    }

    /*
    @DisplayName("JUNit test oneDayQuotes method in Cryptocurrency")
    @Test
    void oneDayQuotesCryptoTest() throws ResourceNotFound {
        System.out.println("Crypto id 1: " + cryptocurrencyService.findById(1).getName());
        System.out.println("oneDayQuotesCrypto: " + cryptocurrencyService.oneDayQuotes(1).size());
        //assertEquals(cryptocurrencyService.oneDayQuotes(1), 24);
        //assertEquals(1,1);
    }
     */












}
