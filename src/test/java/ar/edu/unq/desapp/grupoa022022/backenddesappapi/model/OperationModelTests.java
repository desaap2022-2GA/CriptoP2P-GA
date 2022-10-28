package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class OperationModelTests {

    public Intention mockIntentionSELL = Mockito.mock(Intention.class);

    public Intention mockIntentionBUY = Mockito.mock(Intention.class);

    public User mockUser = Mockito.mock(User.class);

    @BeforeEach
    public void init() {
        Mockito.when(mockIntentionSELL.getType()).thenReturn(IntentionType.SELL);
    }

    @Test
    void ObtainActiveOperationStateAfterCreation() {
        Operation operation = new Operation();

        Assertions.assertEquals(OperationState.ACTIVE, operation.getState());
    }

    @Test
    void ObtainIntentionTypeAfterAOperationIsCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(mockIntentionSELL, mockUser);

        assertEquals(IntentionType.SELL, operation.getIntention().getType());
    }

    @Test
    void ObtainCVUMercadoPagoFromUserOnOperationCreatedWhitAnIntentionTypedAsSell() {
        Operation operation = new Operation(mockIntentionSELL, mockUser);
        Mockito.when(mockIntentionSELL.transactionInfoToShow(any())).thenReturn("6352879863528798635287");

        assertEquals("6352879863528798635287", operation.getIntention().transactionInfoToShow(operation.getUserWhoAccepts()));
    }

    @Test
    void ObtainAddressWalletCryptoFromUserOnOperationCreatedWhitAnIntentionTypedAsBuy() {
        Operation operation = new Operation(mockIntentionBUY, mockUser);
        Mockito.when(mockIntentionBUY.transactionInfoToShow(any())).thenReturn("Xwf5u5ef");

        assertEquals("Xwf5u5ef", operation.getIntention().transactionInfoToShow(operation.getUserWhoAccepts()));
    }
}