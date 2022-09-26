package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
class OperationTests {

    DataSetTest dataSetTest = new DataSetTest();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IOperationRepo operationRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Test
    void ObtainActiveOperationStateAfterCreation() {
        Operation operation = new Operation();

        assertEquals(OperationState.ACTIVE, operation.getState());
    }

    @Test
    void ObtainIntentionTypeAfterAOperationIsCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSetTest.getIntentionSell(), dataSetTest.getUserTest());

        assertEquals(IntentionType.SELL, operation.getType());
    }

    @Test
    void ObtainCVUMercadoPagoFromUserOnOperationCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSetTest.getIntentionSell(), dataSetTest.getUserTest());

        assertEquals("6352879863528798635287", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainAdressWalletCryptoFromUserOnOperationCreatedWhitAnIntentionTypedAsBuy() {
        Operation operation = new Operation(dataSetTest.getIntentionBuy(), dataSetTest.getUserTest());

        assertEquals("Xwf5u5ef", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainUserReputationOnOperationCreatedWhitAnIntentionFromAnUserWithoutPoints() {
        Operation operation = new Operation(dataSetTest.getIntentionBuy(), dataSetTest.getUserTest());

        assertEquals(0, operation.getUserReputation());
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewOperation() {
        User someuserDB = userRepo.save(dataSetTest.getUserTest());
        User someuser2DB = userRepo.save(dataSetTest.getUserTest2());
        Cryptocurrency somecryptocurrencyDB = cryptocurrencyRepo.save(dataSetTest.getCryptocurrency4());
        Intention intentionDB = intentionRepo.save(new Intention(dataSetTest.getSomeType(), somecryptocurrencyDB,
                dataSetTest.getSomePrice(), dataSetTest.getSomeUnit(), someuserDB));
        Operation saved = operationRepo.save(new Operation(intentionDB,someuser2DB));

        int idSaved = saved.getId();
        assertEquals(operationRepo.findById(idSaved).get().getId(), idSaved);
    }
}