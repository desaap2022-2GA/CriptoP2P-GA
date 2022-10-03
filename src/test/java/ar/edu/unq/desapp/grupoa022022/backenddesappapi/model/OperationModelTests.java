package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OperationModelTests {

    DataSet dataSet = new DataSet();

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

        Assertions.assertEquals(OperationState.ACTIVE, operation.getState());
    }

    @Test
    void ObtainIntentionTypeAfterAOperationIsCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSet.getIntentionSell(), dataSet.getUserTest());

        assertEquals(IntentionType.SELL, operation.getType());
    }

    @Test
    void ObtainCVUMercadoPagoFromUserOnOperationCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSet.getIntentionSell(), dataSet.getUserTest());

        assertEquals("6352879863528798635287", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainAdressWalletCryptoFromUserOnOperationCreatedWhitAnIntentionTypedAsBuy() {
        Operation operation = new Operation(dataSet.getIntentionBuy(), dataSet.getUserTest());

        assertEquals("Xwf5u5ef", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainUserReputationOnOperationCreatedWhitAnIntentionFromAnUserWithoutPoints() {
        Operation operation = new Operation(dataSet.getIntentionBuy(), dataSet.getUserTest());

        assertEquals(0, operation.getUserReputation());
    }
}