package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OperationTests {

    DataSetTest dataSetTest = new DataSetTest();

    @Test
    void ObtainActiveOperationStateAfterCreation() {
        Operation operation = new Operation();
        assertEquals(OperationState.ACTIVE, operation.getState());
    }

    @Test
    void ObtainIntentionTypeAfterAOperationIsCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSetTest.getIntentionSell());
        assertEquals(IntentionType.SELL, operation.getType());
    }

    @Test
    void ObtainCVUMercadoPagoFromUserOnOperationCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(dataSetTest.getIntentionSell());
        assertEquals("6352879863528798635287", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainAdressWalletCryptoFromUserOnOperationCreatedWhitAnIntentionTypedAsBuy() {
        Operation operation = new Operation(dataSetTest.getIntentionBuy());
        assertEquals("Xwf5u5ef", operation.getTransactionInfoToShow());
    }

    @Test
    void ObtainUserReputationOnOperationCreatedWhitAnIntentionFromAnUserWithoutPoints() {
        Operation operation = new Operation(dataSetTest.getIntentionBuy());
        assertEquals(0,operation.getUserReputation());
    }
}
