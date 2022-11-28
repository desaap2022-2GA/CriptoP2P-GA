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

    public OperationRegisterDTO mockOperationReg1 = Mockito.mock(OperationRegisterDTO.class);
    public OperationRegisterDTO mockOperationReg2 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg3 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg4 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg5 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg6 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg7 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg8 = Mockito.mock(OperationRegisterDTO.class);

    public OperationRegisterDTO mockOperationReg9 = Mockito.mock(OperationRegisterDTO.class);

    public OperationModifyDTO mockOperationModifyDTO = Mockito.mock(OperationModifyDTO.class);

    public Intention mockIntention = Mockito.mock(Intention.class);

    public IntentionRegisterDTO mockIntentionReg = Mockito.mock(IntentionRegisterDTO.class);

    public IntentionViewDTO mockIntentionViewDTO = Mockito.mock(IntentionViewDTO.class);
    public User mockUser = Mockito.mock(User.class);



    @BeforeEach
    void init(){
        Mockito.when(mockOperationReg1.getIntentionId()).thenReturn(3);
        Mockito.when(mockOperationReg1.getUserId()).thenReturn(1);
        Mockito.when(mockOperationReg2.getIntentionId()).thenReturn(3);
        Mockito.when(mockOperationReg2.getUserId()).thenReturn(1);
        Mockito.when(mockOperationReg3.getIntentionId()).thenReturn(4);
        Mockito.when(mockOperationModifyDTO.getOperationId()).thenReturn(1);
        Mockito.when(mockOperationModifyDTO.getUserId()).thenReturn(1);
        Mockito.when(mockOperationModifyDTO.getState()).thenReturn(PAID);

        Mockito.when(mockIntention.getId()).thenReturn(1);
        Mockito.when(mockIntention.getType()).thenReturn(IntentionType.valueOf("BUY"));
        Mockito.when(mockIntention.getPrice()).thenReturn(304.75d);
        Mockito.when(mockIntention.getUnits()).thenReturn(2);
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockIntentionReg.getUserId()).thenReturn(1);
        Mockito.when(mockIntentionReg.getCryptocurrencyId()).thenReturn(1);
        Mockito.when(mockIntentionViewDTO.getId()).thenReturn(String.valueOf(1));
        Mockito.when(mockIntentionViewDTO.getAmountPriceInPesos()).thenReturn(String.valueOf(200d));
        Mockito.when(mockIntentionViewDTO.getCryptocurrency()).thenReturn("DAI");
        Mockito.when(mockIntentionViewDTO.getUnits()).thenReturn(String.valueOf(1));
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockUser.getId()).thenReturn(1);

        operationService.deleteAll();

    }

    // *** PONER LOS COMENTADOS ABAJO!!!!!!!!!!!!!!!! ***

    @Order(1)
    @DisplayName("JUnit test create method in OperationService")
    @Test
    void createOperationTest() throws PriceNotInAValidRangeException, ResourceNotFoundException, PriceNotInAValidRangeException, PriceExceedVariationWithRespectIntentionTypeLimitsException, IntentionAlreadyTakenException {
        //cambiar id intention
        IntentionRegisterDTO intentionRegisterDTO = new IntentionRegisterDTO(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegisterDTO);

        OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegisterDTO);

        assertEquals(operation.getIntention().getId(), 4);
    }

    @Order(2)
    @DisplayName("JUnit test create method with exception in OperationService")
    @Test
    void createOperation_With_Exceptition_Test(){
        assertThrows(IntentionAlreadyTakenException.class, () -> {
            OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(mockOperationReg3.getIntentionId(), mockOperationReg1.getUserId());
            operationService.create(operationRegisterDTO);
        });
    }

    @Order(3)
    @DisplayName("JUnit test open method in OperationService")
    @Test
    void openOperationTest() throws PriceNotInAValidRangeException, ResourceNotFoundException, PriceExceedVariationWithRespectIntentionTypeLimitsException, PriceNotInAValidRangeException, IntentionAlreadyTakenException {
        IntentionRegisterDTO intentionRegisterDTO = new IntentionRegisterDTO(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegisterDTO);

        OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(intent.getId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegisterDTO);

        assertEquals(operation.getState(),ACTIVE);
    }

    @Order(4)
    @DisplayName("JUnit test open method with exception in OperationService")
    @Test
    void openOperation_With_Exception_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
            OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(mockOperationReg5.getIntentionId(), mockOperationReg1.getUserId());
            operationRegisterDTO.setUserId(50);
            operationService.create(operationRegisterDTO);
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
        assertThrows(ResourceNotFoundException.class, () ->{
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
    void findByIdOperationTest() throws ResourceNotFoundException, PriceNotInAValidRangeException, PriceExceedVariationWithRespectIntentionTypeLimitsException, PriceNotInAValidRangeException, IntentionAlreadyTakenException {
        //crear operacion
        //Operation operation = operationService.findById(1);
        IntentionRegisterDTO intentionRegisterDTO = new IntentionRegisterDTO(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegisterDTO);

        OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegisterDTO);

        assertEquals(operation.getId(), 4);
    }

    @Order(10)
    @DisplayName("JUnit test findById method with exception in OperationService")
    @Test
    void findById_With_Exception_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
            operationService.findById(20);
        });
    }

    @Order(11)
    @DisplayName("JUnit test getOperationById method in OperationService")
    @Test
    void getOperationByIdTest() throws ResourceNotFoundException, PriceNotInAValidRangeException, PriceExceedVariationWithRespectIntentionTypeLimitsException, PriceNotInAValidRangeException, IntentionAlreadyTakenException {
        //crear operation
        //OperationView operation = operationService.getOperationById(1, 1);
        IntentionRegisterDTO intentionRegisterDTO = new IntentionRegisterDTO(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegisterDTO);

        OperationRegisterDTO operationRegisterDTO = new OperationRegisterDTO(intent.getId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegisterDTO);

        OperationViewDTO operationViewDTO = operationService.getOperationById(operation.getId(), 1);

        assertEquals(operationViewDTO.getOperationNumber(), 1);
    }

    @Order(12)
    @DisplayName("JUNit test getOperationById method with exception in OperationService")
    @Test
    void getOperationById_With_Exception_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
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
        assertThrows(ResourceNotFoundException.class, () -> {
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
        assertThrows(ResourceNotFoundException.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.moneyTransferDone(operation);
        });
    }

  /*  @Order(17)
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

   */

    @Order(18)
    @DisplayName("JUni cryptoSendDone method with exception in OperationService")
    @Test
    void cryptoSendDoneTest_With_Exception_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
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
        assertThrows(ResourceNotFoundException.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.assignBonusTimeToUsers(operation);
        });
    }

 /*   @Order(21)
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

  */

    @Order(22)
    @DisplayName("JUnit modify method with exception in OperationService")
    @Test
    void modify_With_Exception_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
            OperationModifyDTO operationModifyDTO = new OperationModifyDTO(105, mockOperationModifyDTO.getState(),
                    mockOperationModifyDTO.getUserId());
            operationService.modify(operationModifyDTO);
        });
    }
}
