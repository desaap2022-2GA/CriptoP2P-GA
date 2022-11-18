package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.OperationService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    public OperationRegister mockOperationReg1 = Mockito.mock(OperationRegister.class);
    public OperationRegister mockOperationReg2 = Mockito.mock(OperationRegister.class);

    public OperationModify mockOperationModify = Mockito.mock(OperationModify.class);



    @BeforeEach
    void init(){
        Mockito.when(mockOperationReg1.getIntentionId()).thenReturn(3);
        Mockito.when(mockOperationReg1.getUserId()).thenReturn(1);
        Mockito.when(mockOperationReg2.getIntentionId()).thenReturn(1);
        Mockito.when(mockOperationReg2.getUserId()).thenReturn(1);
        Mockito.when(mockOperationModify.getOperationId()).thenReturn(1);
        Mockito.when(mockOperationModify.getUserId()).thenReturn(1);
        Mockito.when(mockOperationModify.getState()).thenReturn(PAID);
    }

    @DisplayName("JUnit test create method in OperationService")
    @Test
    void createOperationTest() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegister);

        assertEquals(operation.getIntention().getId(), 3);
    }

    @DisplayName("JUnit test create method with exception in OperationService")
    @Test
    void createOperation_With_Exceptition_Test(){
        assertThrows(PriceExceedVariationWithRespectIntentionTypeLimits.class, () -> {
            OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());
            operationRegister.setIntentionId(2);
            operationService.create(operationRegister);
        });
    }

    @DisplayName("JUnit test open method in OperationService")
    @Test
    void openOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());

        Operation operation = operationService.create(operationRegister);

        assertEquals(operation.getUserWhoAccepts().getId(),1);
    }

    @DisplayName("JUnit test open method with exception in OperationService")
    @Test
    void openOperation_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());
            operationRegister.setUserId(50);
            operationService.create(operationRegister);
        });
    }


    @DisplayName("JUnit test update method in OperationService")
    @Test
    void updateOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.findById(1);
        operation.setState(OperationState.valueOf("PAID"));
        operationService.update(operation);

        assertEquals(operation.getState(), PAID);
    }


    @DisplayName("JUnit test update method in OperationService")
    @Test
    void updateOperation_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () ->{
            Operation operation = operationService.findById(20);
            operation.setDateTime(21546300000l);
            operationService.update(operation);
        });
    }

    @DisplayName("JUnit test delete method in OperationService")
    @Test
    void deleteOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        OperationRegister operationRegister = new OperationRegister(mockOperationReg1.getIntentionId(), mockOperationReg1.getUserId());
        Operation operation = operationService.create(operationRegister);

        operationService.delete(operation.getId());

        assertEquals(operationService.getAll().size(), 1);
    }

    @DisplayName("JUnit test deleteAll method in OperationService")
    @Test
    void deleteAllOperationTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        operationService.deleteAll();

        assertTrue(operationService.getAll().isEmpty());
    }

    @DisplayName("JUnit test findById method in OperationService")
    @Test
    void findByIdOperationTest() throws ResourceNotFound {
        Operation operation = operationService.findById(1);

        assertEquals(operation.getId(), 1);
    }

    @DisplayName("JUnit test findById method with exception in OperationService")
    @Test
    void findById_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            operationService.findById(20);
        });
    }

    @DisplayName("JUnit test getOperationById method in OperationService")
    @Test
    void getOperationByIdTest() throws ResourceNotFound {
        OperationView operation = operationService.getOperationById(1, 1);

        assertEquals(operation.getOperationNumber(), 1);
    }

    @DisplayName("JUNit test getOperationById method with exception in OperationService")
    @Test
    void getOperationById_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            operationService.getOperationById(1000, 55);
        });
    }

    @DisplayName("JUnit test cancelOperationByUser method in OperationService")
    @Test
    void cancelOperationByUserTest() throws IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.findById(1);
        User user = operation.getUserWhoAccepts();

        operationService.cancelOperationByUser(operation, user);

        assertEquals(operation.getState(), CANCELLED);
    }

    @DisplayName("JUnit test cancelOperationByUser method with exception in OperationService")
    @Test
    void cancelOperationByUser_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(10);
            User user = operation.getUserWhoAccepts();
            operationService.cancelOperationByUser(operation, user);
        });
    }

    @DisplayName("JUnit moneyTransferDone method in OperationService")
    @Test
    void moneyTransferDoneTest() throws ResourceNotFound {
        Operation operation = operationService.findById(1);
        operationService.moneyTransferDone(operation);

        assertEquals(operation.getState(), PAID);
    }


    @DisplayName("JUnit moneyTransferDone method with exception in Operation Service")
    @Test
    void moneyTransferDone_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.moneyTransferDone(operation);
        });
    }

    @DisplayName("JUnit cryptoSendDone method in OperationService")
    @Test
    void cryptoSendDoneTest() throws ResourceNotFound {
        Operation operation = operationService.findById(1);
        operationService.cryptoSendDone(operation);

        assertEquals(operation.getState(), CRYPTOSENT);
    }

    @DisplayName("JUni cryptoSendDone method with exception in OperationService")
    @Test
    void cryptoSendDoneTest_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.cryptoSendDone(operation);
        });
    }

    @DisplayName("Junit assignBonusTimeToUsers method in OperationService")
    @Test
    void assignBonusTimeToUsersTest() throws ResourceNotFound {
        Operation operation = operationService.findById(1);
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(operation.getUserWhoAccepts().getOperations().size(), 1);
    }

    @DisplayName("JUnit assignBonusTimeToUsers method with exception in OperationService")
    @Test
    void assignBonusTimeToUsers_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            Operation operation = operationService.findById(100);
            operationService.assignBonusTimeToUsers(operation);
        });
    }

    @DisplayName("JUnit modify method in OperationService")
    @Test
    void modifyTest() throws ResourceNotFound, InvalidState {
        OperationModify operationModify = new OperationModify(mockOperationModify.getOperationId(), mockOperationModify.getState(),
                mockOperationModify.getUserId());
        operationService.modify(operationModify);

        assertEquals(operationModify.getState(), PAID);
    }

    @DisplayName("JUnit modify method with exception in OperationService")
    @Test
    void modify_With_Exception_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            OperationModify operationModify = new OperationModify(5, mockOperationModify.getState(),
                    mockOperationModify.getUserId());
            operationService.modify(operationModify);
        });
    }
}
