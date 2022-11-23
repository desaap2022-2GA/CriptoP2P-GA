package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.IntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.OperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IntentionServiceTest {

    @Autowired
    private IntentionService intentionService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserService userService;

    public Intention mockIntention = Mockito.mock(Intention.class);

    public IntentionRegister mockIntentionReg = Mockito.mock(IntentionRegister.class);

    public IntentionView mockIntentionView = Mockito.mock(IntentionView.class);
    public User mockUser = Mockito.mock(User.class);

    @BeforeEach
    public void init() {
        Mockito.when(mockIntention.getId()).thenReturn(1);
        Mockito.when(mockIntention.getType()).thenReturn(IntentionType.valueOf("BUY"));
        Mockito.when(mockIntention.getPrice()).thenReturn(289.75d);
        Mockito.when(mockIntention.getUnits()).thenReturn(2);
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockIntentionReg.getUserId()).thenReturn(1);
        Mockito.when(mockIntentionReg.getCryptocurrencyId()).thenReturn(1);
        Mockito.when(mockIntentionView.getId()).thenReturn(String.valueOf(1));
        Mockito.when(mockIntentionView.getAmountPriceInPesos()).thenReturn(String.valueOf(200d));
        Mockito.when(mockIntentionView.getCryptocurrency()).thenReturn("DAI");
        Mockito.when(mockIntentionView.getUnits()).thenReturn(String.valueOf(1));
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        Mockito.when(mockUser.getId()).thenReturn(1);

        operationService.deleteAll();
        intentionService.deleteAll();
    }


    @DisplayName("JUnit test create method in IntentionService")
    @Test
    void createIntentionTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
        Intention intent = intentionService.create(intentionRegister);
        assertEquals(intent.getPrice(), intentionRegister.getPrice());
    }

    @DisplayName("JUnit test create method with exception in IntentionService")
    @Test
    void createIntention_WithException_Test(){
        assertThrows(PriceNotInAValidRangeException.class, () -> {
            IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                    mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
            intentionRegister.setPrice(50000d);
            intentionService.create(intentionRegister);
        });
    }

    @DisplayName("JUnit test open method in IntentionService")
    @Test
    void openIntentionTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        assertEquals(intentionService.open(intentionRegister).getPrice(), "289.75");
    }

    @DisplayName("JUnit test open method with exception in IntentionService")
    @Test
    void openIntention_WithException_Test(){
        assertThrows(PriceNotInAValidRangeException.class, () -> {
            IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                    mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());
            intentionRegister.setPrice(50d);
            intentionService.open(intentionRegister);
        });
    }

    @DisplayName("JUnit test update method in IntentionService")
    @Test
    void updateIntentionTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);
        intent.setUnits(3);
        intentionService.update(intent);

        assertEquals(intent.getUnits(), 3);
    }

    @DisplayName("JUnit test deleteById Method in IntentionService")
    @Test
    void deleteByIdIntentionTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        intentionService.delete(intent.getId());

        assertEquals(intentionService.getAll().size(), 0);
    }

    @DisplayName("JUnit test getIntentionById method in IntentionService")
    @Test
    void getIntentionByIdTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        List<IntentionView> intentions = intentionService.getAll();

        assertEquals(intentionService.getIntentionById(intent.getId()).getUnits(), "2");
    }

    @DisplayName("JUnit test getIntentionById method with exception in IntentionService")
    @Test
    void getIntentionById_WithException_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
            intentionService.getIntentionById(20);
        });
    }


    @DisplayName("JUnit test getActiveIntention method in IntentionService")
    @Test
    void getActiveIntentionsTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        assertEquals(intentionService.getActiveIntentions().size(), 1);
    }

    @DisplayName("JUnit test findById method in IntentionService")
    @Test
    void findByIdIntentionTest() throws PriceNotInAValidRangeException, ResourceNotFoundException {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);
        List<IntentionView> intentions = intentionService.getAll();

        assertEquals(intentionService.findById(intent.getId()).getPrice(), 289.75d);
    }

    @DisplayName("JUnit test findIntentionById method with exception in IntentionService")
    @Test
    void findIntentionById_WithException_Test(){
        assertThrows(ResourceNotFoundException.class, () -> {
            intentionService.getIntentionById(20);
        });
    }

    @DisplayName("Junit test findActiveIntentions method in IntentionService")
    @Test
    void findActiveIntentionTest(){
        assertEquals(intentionService.findActiveIntentions().size(),0);
    }

    @DisplayName("JUnit test getAll method in IntentionService")
    @Test
    void getAllIntentionTest() {

        assertEquals(intentionService.getAll().size(), 0);
    }

    @DisplayName("JUnit test deleteAll method in IntentionService")
    @Test
    void deleteAllIntentionsTest(){
        operationService.deleteAll();
        intentionService.deleteAll();

        assertTrue(intentionService.getAll().isEmpty());
    }
}
