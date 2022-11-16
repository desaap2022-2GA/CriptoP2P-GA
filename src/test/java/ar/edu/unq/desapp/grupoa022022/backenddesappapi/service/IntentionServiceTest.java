package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.IntentionService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IntentionServiceTest {

    @Autowired
    private IntentionService intentionService;

    @Autowired
    private UserService userService;

    DataSet dataSet;


    public Intention mockIntention = Mockito.mock(Intention.class);

    public IntentionRegister mockIntentionReg = Mockito.mock(IntentionRegister.class);

    public IntentionView mockIntentionView = Mockito.mock(IntentionView.class);

    public IntentionType mockintentionType = Mockito.mock(IntentionType.class);
    public User mockUser = Mockito.mock(User.class);

    public User userReg = new User("Paston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1234", "6352879863528798635287",
            "Xwf5u5ef");

    public UserView userView = new UserView(1, "Paston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "6352879863528798635287",
            "Xwf5u5ef", 2, 1, 1);


    public Cryptocurrency cryptocurrency = new Cryptocurrency("DAI", 320.38);


    @BeforeEach
    public void init() throws ResourceNotFound {
        Mockito.when(mockIntention.getId()).thenReturn(1);
        Mockito.when(mockIntention.getType()).thenReturn(IntentionType.valueOf("BUY"));
        Mockito.when(mockIntention.getPrice()).thenReturn(289.75d);
        Mockito.when(mockIntention.getUnits()).thenReturn(2);
        Mockito.when(mockIntention.isTaken()).thenReturn(true);
        Mockito.when(mockIntentionReg.getUserId()).thenReturn(1);
        Mockito.when(mockIntentionReg.getCryptocurrencyId()).thenReturn(1);
        Mockito.when(mockIntentionView.getId()).thenReturn(1);
        Mockito.when(mockIntentionView.getAmountPriceInPesos()).thenReturn(200d);
        Mockito.when(mockIntentionView.getCryptocurrency()).thenReturn("DAI");
        Mockito.when(mockIntentionView.getUnits()).thenReturn(1);
        Mockito.when(mockIntention.isTaken()).thenReturn(false);
        //Mockito.when(mockintentionType).thenReturn(IntentionType.BUY);

        Mockito.when(mockUser.getId()).thenReturn(1);


    }

    @DisplayName("JUnit test create method in IntentionService")
    @Test
    public void createIntentionTest() throws PriceNotInAValidRange, ResourceNotFound {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        assertEquals(intent.getPrice(), intentionRegister.getPrice());
    }


    @DisplayName("JUnit test getAll method in IntentionService")
    @Test
    public void getAllIntentionTest() {
        assertEquals(intentionService.getAll().size(), 3);
    }

    @DisplayName("JUnit test findById method in IntentionService")
    @Test
    public void findByIdIntentionTest() throws ResourceNotFound {
        assertEquals(intentionService.findById(1).getPrice(), 289.75d);
    }

    @DisplayName("JUnit test open method in IntentionService")
    @Test
    public void openIntentionTest() throws PriceNotInAValidRange, ResourceNotFound {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        assertEquals(intentionService.open(intentionRegister).getPrice(), 289.75d);

    }


    @DisplayName("JUnit test update method in IntentionService")
    @Test
    public void updateIntentionTest() throws PriceNotInAValidRange, ResourceNotFound {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        intentionService.update(intent);

        assertEquals(intent.getPrice(), 289.75d);
    }

    @DisplayName("JUnit test deleteById Method in IntentionService")
    @Test
    public void deleteByIdIntentionTest() throws PriceNotInAValidRange, ResourceNotFound {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        intentionService.delete(intent.getId());

        assertEquals(intentionService.getAll().size(), 3);
    }

    @DisplayName("JUnit test getActiveIntetntion method in IntentionService")
    @Test
    public void getActiveIntentionsTest() throws PriceNotInAValidRange, ResourceNotFound {
        IntentionRegister intentionRegister = new IntentionRegister(mockIntention.getType(), mockIntentionReg.getCryptocurrencyId(),
                mockIntention.getPrice(), mockIntention.getUnits(), mockIntentionReg.getUserId());

        Intention intent = intentionService.create(intentionRegister);

        List<IntentionView> intentions = intentionService.getActiveIntentions();

        assertEquals(intentions.size(), 0);
    }



/*
public List<IntentionView> getActiveIntentions() {
        return this.findActiveIntentions().stream().map(intention -> helper.intentionToIntentionView(intention
                ,intention.getUser())).toList();
    }
 */


}

/*
    Intention findById(int id) throws ResourceNotFound;
    IntentionView getIntentionById(int id) throws ResourceNotFound;
*/