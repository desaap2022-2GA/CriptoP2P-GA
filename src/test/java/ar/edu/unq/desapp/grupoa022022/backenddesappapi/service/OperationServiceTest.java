package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.IntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.OperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    @Autowired
    private IntentionService intentionService;

    public OperationRegister mockOperationReg1 = Mockito.mock(OperationRegister.class);
    public OperationRegister mockOperationReg2 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg3 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg4 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg5 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg6 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg7 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg8 = Mockito.mock(OperationRegister.class);

    public OperationRegister mockOperationReg9 = Mockito.mock(OperationRegister.class);

    public OperationModify mockOperationModify = Mockito.mock(OperationModify.class);

    public Intention mockIntention = Mockito.mock(Intention.class);

    public IntentionRegister mockIntentionReg = Mockito.mock(IntentionRegister.class);

    public IntentionView mockIntentionView = Mockito.mock(IntentionView.class);
    public User mockUser = Mockito.mock(User.class);



    @BeforeEach
    void init(){
        Mockito.when(mockOperationReg1.getIntentionId()).thenReturn(3);
        Mockito.when(mockOperationReg1.getUserId()).thenReturn(1);
        Mockito.when(mockOperationReg2.getIntentionId()).thenReturn(3);
        Mockito.when(mockOperationReg2.getUserId()).thenReturn(1);
        Mockito.when(mockOperationReg3.getIntentionId()).thenReturn(4);
        Mockito.when(mockOperationModify.getOperationId()).thenReturn(1);
        Mockito.when(mockOperationModify.getUserId()).thenReturn(1);
        Mockito.when(mockOperationModify.getState()).thenReturn(PAID);

        Mockito.when(mockIntention.getId()).thenReturn(1);
        Mockito.when(mockIntention.getType()).thenReturn(IntentionType.valueOf("BUY"));
        Mockito.when(mockIntention.getPrice()).thenReturn(289.75d);
        Mockito.when(mockIntention.getUnits()).thenReturn(2);
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockIntentionReg.getUserId()).thenReturn(1);
        Mockito.when(mockIntentionReg.getCryptocurrencyId()).thenReturn(1);
        Mockito.when(mockIntentionView.getId()).thenReturn(1);
        Mockito.when(mockIntentionView.getAmountPriceInPesos()).thenReturn(200d);
        Mockito.when(mockIntentionView.getCryptocurrency()).thenReturn("DAI");
        Mockito.when(mockIntentionView.getUnits()).thenReturn(1);
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockUser.getId()).thenReturn(1);

        operationService.deleteAll();

    }

    // *** PONER LOS COMENTADOS ABAJO!!!!!!!!!!!!!!!! ***

    @Order(1)
    @DisplayName("JUnit test create method in OperationService")
    @Test
    void createOperationTest() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        //cambiar id intention
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        assertEquals(operation.getIntention().getId(), 4);
    }

    @Order(2)
    @DisplayName("JUnit test create method with exception in OperationService")
    @Test
    void createOperation_With_Exceptition_Test(){
        assertThrows(IntentionAlreadyTaken.class, () -> {
            OperationRegister operationRegister = new OperationRegister(mockOperationReg3.getIntentionId(), mockOperationReg1.getUserId());
            operationService.create(operationRegister);
        });
    }

    @Order(3)
    @DisplayName("JUnit test open method in OperationService")
    @Test
    void openOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegister);

        assertEquals(operation.getState(),ACTIVE);
    }

    @Order(4)
    @DisplayName("JUnit test open method with exception in OperationService")
    @Test
    void openOperation_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            OperationRegister operationRegister = new OperationRegister(mockOperationReg5.getIntentionId(), mockOperationReg1.getUserId());
            operationRegister.setUserId(50);
            operationService.create(operationRegister);
        });
    }

/*    @Order(5)
    @DisplayName("JUnit test update method in OperationService")
    @Test
    void updateOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        //crear la operacion
        //Operation operation = operationService.findById(1);

//        List<IntentionView> intentionsActive = intentionService.getActiveIntentions();

//        System.out.println("intentions active: " + intentionsActive);

        //operationService.deleteAll();
        //intentionService.deleteAll();
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegister);

        operation.setState(PAID);
        operationService.update(operation);

        assertEquals(operation.getState(), PAID);
    }

 */


    @Order(6)
    @DisplayName("JUnit test update method in OperationService")
    @Test
    void updateOperation_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () ->{
            Operation operation = operationService.findById(20);
            operation.setDateTime(21546300000l);
            operationService.update(operation);
        });
    }
/*
    @Order(7)
    @DisplayName("JUnit test delete method in OperationService")
    @Test
    void deleteOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        operationService.delete(operation.getId());

        assertEquals(operationService.getAll().size(), 1);
    }

    @Order(8)
    @DisplayName("JUnit test deleteAll method in OperationService")
    @Test
    void deleteAllOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        operationService.deleteAll();

        assertTrue(operationService.getAll().isEmpty());
    }
*/
    @Order(9)
    @DisplayName("JUnit test findById method in OperationService")
    @Test
    void findByIdOperationTest() throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        //crear operacion
        //Operation operation = operationService.findById(1);
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        assertEquals(operation.getId(), 4);
    }

    @Order(10)
    @DisplayName("JUnit test findById method with exception in OperationService")
    @Test
    void findById_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            operationService.findById(20);
        });
    }

    @Order(11)
    @DisplayName("JUnit test getOperationById method in OperationService")
    @Test
    void getOperationByIdTest() throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        //crear operation
        //OperationView operation = operationService.getOperationById(1, 1);
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        OperationView operationView = operationService.getOperationById(operation.getId(), 1);

        assertEquals(operationView.getOperationNumber(), 1);
    }

    @Order(12)
    @DisplayName("JUNit test getOperationById method with exception in OperationService")
    @Test
    void getOperationById_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            operationService.getOperationById(1000, 55);
        });
    }

 /*   @Order(13)
    @DisplayName("JUnit test cancelOperationByUser method in OperationService")
    @Test
    void cancelOperationByUserTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {

        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        //Operation operation = operationService.findById(1);

        User user = operation.getUserWhoAccepts();

        operationService.cancelOperationByUser(operation, user);

        assertEquals(operation.getState(), CANCELLED);
    }

  */

    @Order(14)
    @DisplayName("JUnit test cancelOperationByUser method with exception in OperationService")
    @Test
    void cancelOperationByUser_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(10);
            User user = operation.getUserWhoAccepts();
            operationService.cancelOperationByUser(operation, user);
        });
    }
/*
    @Order(15)
    @DisplayName("JUnit moneyTransferDone method in OperationService")
    @Test
    void moneyTransferDoneTest() throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        //create operation
        //Operation operation = operationService.findById(1);
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);
        //Operation operation = operationService.findById(1);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        operationService.moneyTransferDone(operation);

        assertEquals(operation.getState(), PAID);
    }

 */


    @Order(16)
    @DisplayName("JUnit moneyTransferDone method with exception in Operation Service")
    @Test
    void moneyTransferDone_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.moneyTransferDone(operation);
        });
    }

    @Order(17)
    @DisplayName("JUnit cryptoSendDone method in OperationService")
    @Test
    void cryptoSendDoneTest() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        //create operation
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        //Operation operation = operationService.findById(1);
        operationService.cryptoSendDone(operation);

        assertEquals(operation.getState(), CRYPTOSENT);
    }

    @Order(18)
    @DisplayName("JUni cryptoSendDone method with exception in OperationService")
    @Test
    void cryptoSendDoneTest_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.cryptoSendDone(operation);
        });
    }

 /*   @Order(19)
    @DisplayName("Junit assignBonusTimeToUsers method in OperationService")
    @Test
    void assignBonusTimeToUsersTest() throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits, PriceNotInAValidRange {
        //create operation
        //Operation operation = operationService.findById(1);
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationRegister operationRegister = new OperationRegister(intent.getId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegister);

        operationService.assignBonusTimeToUsers(operation);

        assertEquals(operation.getUserWhoAccepts().getOperations().size(), 1);
    }

  */

    @Order(20)
    @DisplayName("JUnit assignBonusTimeToUsers method with exception in OperationService")
    @Test
    void assignBonusTimeToUsers_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.assignBonusTimeToUsers(operation);
        });
    }

    @Order(21)
    @DisplayName("JUnit modify method in OperationService")
    @Test
    void modifyTest() throws ResourceNotFound, InvalidState, PriceNotInAValidRange {
        //debaguear
        //probar creando una operation
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);

        OperationModify operationModify = new OperationModify(intent.getId(), mockOperationModify.getState(),
                mockOperationModify.getUserId());
        operationModify.setState(PAID);
        operationService.modify(operationModify);

        assertEquals(operationModify.getState(), PAID);
    }

    @Order(22)
    @DisplayName("JUnit modify method with exception in OperationService")
    @Test
    void modify_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            OperationModify operationModify = new OperationModify(105, mockOperationModify.getState(),
                    mockOperationModify.getUserId());
            operationService.modify(operationModify);
        });
    }
}
