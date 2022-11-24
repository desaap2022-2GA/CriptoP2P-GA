package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class OperationPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IOperationRepo operationRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;
    @Autowired
    IUserService userService;
    @Autowired
    IQuoteService quoteService;
    @Autowired
    ICryptocurrencyService cryptocurrencyService;
    @Autowired
    IIntentionService intentionService;
    @Autowired
    IOperationService operationService;

    public Cryptocurrency getCryptocurrencyDB() {
        return cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
    }

    public Cryptocurrency getCryptocurrencyDB2() {
        return cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterBITCOIN());
    }

    public User getUserWhoPostDB() throws UserValidationException {
        return userService.saveToDataBase(dataSet.getUserRegister());
    }

    public User getUserWhoAcceptDB2() throws UserValidationException {
        return userService.saveToDataBase(dataSet.getUserRegister2());
    }

    public User getUserWith30Point3NumberOperationsDB() throws UserValidationException {
        User userDB = getUserWhoPostDB();
        userDB.setPoints(30);
        userDB.setNumberOperations(3);
        return userRepo.save(userDB);
    }

    public int getUserWhoPostDBId() throws UserValidationException {
        return getUserWhoPostDB().getId();
    }

    public int getUserWhoAcceptDB2Id() throws UserValidationException {
        return getUserWhoAcceptDB2().getId();
    }

    public int getUserWith30Point3NumberOperationsDBId() throws UserValidationException {
        return getUserWith30Point3NumberOperationsDB().getId();
    }

    public int getSomeCryptocurrencyDBId() {
        return getCryptocurrencyDB().getId();
    }

    public int getSomeCryptocurrencyDB2Id() {
        return getCryptocurrencyDB2().getId();
    }

    public IntentionRegisterDTO getSomeIntentionRegister() throws UserValidationException {
        return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(),
                getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegisterDTO getIntentionRegisterWithPrice320Units2() throws UserValidationException {
        return new IntentionRegisterDTO(IntentionType.SELL,
                getSomeCryptocurrencyDBId(), 320.00, 2, getUserWhoPostDBId());
    }

    public IntentionRegisterDTO getIntentionRegisterWithPrice330Units2() throws UserValidationException {
        return new IntentionRegisterDTO(IntentionType.BUY,
                getSomeCryptocurrencyDBId(), 330.00, 2, getUserWhoPostDBId());
    }

    public IntentionRegisterDTO getIntentionRegisterWithUserWhoHas30Point3NumberOperations() throws UserValidationException {
        return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(), getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(),
                dataSet.getSomeUnit(), getUserWith30Point3NumberOperationsDBId());
    }

    public IntentionRegisterDTO getIntentionRegisterBUYType() throws UserValidationException {
        return new IntentionRegisterDTO(IntentionType.BUY,
                getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegisterDTO getIntentionRegisterSELLType() throws UserValidationException {
        return new IntentionRegisterDTO(IntentionType.SELL,
                getSomeCryptocurrencyDB2Id(), dataSet.getSomePriceInRangeBITCOIN(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public Intention getIntentionRegisterWithPrice320Units2DB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getIntentionRegisterWithPrice320Units2());
    }


    public Intention getIntentionRegisterWithPrice330Units2DB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getIntentionRegisterWithPrice330Units2());
    }

    public Intention getSELLTypeIntentionDB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getIntentionRegisterSELLType());
    }

    public Intention getBUYTypeIntentionDB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getIntentionRegisterBUYType());
    }

    public Intention getIntentionDB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getSomeIntentionRegister());
    }

    public Intention getIntentionWhoUserHas30Points3NumberOperationsDB() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return intentionService.create(getIntentionRegisterWithUserWhoHas30Point3NumberOperations());
    }

    public int getIntentionDBId() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return getIntentionDB().getId();
    }

    public int getSELLIntentionDBId() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return getSELLTypeIntentionDB().getId();
    }

    public int getBUYIntentionDBId() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return getBUYTypeIntentionDB().getId();
    }

    public OperationRegisterDTO getOperationRegisterWithUserPostWhoHas30Point3NumberOperations() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return new OperationRegisterDTO(getIntentionWhoUserHas30Points3NumberOperationsDB().getId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegisterDTO getBUYOperationRegister() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return new OperationRegisterDTO(getBUYIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegisterDTO getSELLOperationRegister() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return new OperationRegisterDTO(getSELLIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegisterDTO getSomeOperationRegister() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return new OperationRegisterDTO(getIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegisterDTO getOperationRegisterWithUserAcceptWhoHas30Point3NumberOperations() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        return new OperationRegisterDTO(getIntentionDBId(), getUserWith30Point3NumberOperationsDBId());
    }

    @BeforeEach
    public void init() {
        //       LOG.info("startup");
        operationService.deleteAll();
        intentionService.deleteAll();
        cryptocurrencyService.deleteAll();
        userService.deleteAllUsers();
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewOperation() throws UserValidationException {
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeTypeBUY(), getCryptocurrencyDB(),
                dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDB()));
        Operation saved = operationRepo.save(new Operation(intentionDB, getUserWhoAcceptDB2()));

        assertEquals(operationRepo.findById(saved.getId()).get().getId(), saved.getId());
    }

    @Test
    void getOperationIdFromANewPersistedOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        int operationId = operationService.create(getSomeOperationRegister()).getId();

        assertEquals(operationId, operationService.findById(operationId).getId());
    }

    @Test
    void getResourceNotFoundWhenAskForOperationThatNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> operationService.findById(1));
    }

    @Test
    void getOperationStateFromAnOperationThatChangeState() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operation.setState(OperationState.CRYPTOSENT);
        operationService.update(operation);

        assertEquals(OperationState.CRYPTOSENT, operationService.findById(operation.getId()).getState());
    }

    @Test
    void getResourceNotFoundAfterDeleteTheOnlyOneAndAskForIt() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.delete(operation.getId());

        assertThrows(ResourceNotFoundException.class, () -> operationService.findById(operation.getId()));
    }

    @Test
    void getSellTypeWhenAskForAnOperationTypeMadeWithASellIntentionType() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());
        assertEquals(IntentionType.SELL, operation.getIntention().getType());
    }

    @Test
    void getMercadoPagoCVUWhenUserWhoPostAskForInfoToShowOnBuyTypeOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("6352879863528798635287", operation.getIntention().transactionInfoToShow(operation.getIntention().getUser()));
    }

    @Test
    void getAddressCryptoWhenUserWhoPostAskForInfoToShowOnSELLTypeOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("Xwf5u5ef", operation.getIntention().transactionInfoToShow(operation.getIntention().getUser()));
    }

    @Test
    void getAddressWalletInfoWhenAskTransactionInfoInABuyIntentionTypeOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Xwf5u5ef", operation.getIntention().transactionInfoToShow(operation.getUserWhoAccepts()));
    }

    @Test
    void getMercadoPagoCvuInfoWhenAskTransactionInfoInASellIntentionTypeOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("6352879863528798635287", operation.getIntention().transactionInfoToShow(operation.getUserWhoAccepts()));
    }

    @Test
    void getUserReputationFromOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getOperationRegisterWithUserPostWhoHas30Point3NumberOperations());

        assertEquals(10, operation.getIntention().getUser().getReputation());
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeSELLAndUserUserWhoAccept() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("Make transfer", operation.actionToDo(operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoWaitingForCounterpartTransferFromOperationWithIntentionTypeSELLAndUserUserWhoPost() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("Waiting for counterpart transfer", operation.actionToDo(operation.getIntention().getUser()));
    }

    @Test
    void getActionToDoWaitingForCounterpartTransferFromOperationWithIntentionTypeBUYAndUserUserWhoAccept() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Waiting for counterpart transfer", operation.actionToDo(operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeBUYAndUserUserWhoPost() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Make transfer", operation.actionToDo(operation.getIntention().getUser()));
    }

    @Test
    void get10WhenAskForPointsFromUserWhoPostWith30PointsAfterCancelOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getOperationRegisterWithUserPostWhoHas30Point3NumberOperations());
        operationService.cancelOperationByUser(operation, operation.getIntention().getUser());

        assertEquals(10, userService.findById(operation.getIntention().getUser().getId()).getPoints());
    }

    @Test
    void get10WhenAskForPointsFromUserWhoAcceptWith30PointsAfterCancelOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getOperationRegisterWithUserAcceptWhoHas30Point3NumberOperations());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void getACTIVEStateFromNewOperation() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        assertEquals(OperationState.ACTIVE, operation.getState());
    }

    @Test
    void getCANCELLEDStateFromOperationAfterCancel() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(OperationState.CANCELLED, operation.getState());
    }

    @Test
    void getPAIDStateFromOperationAfterMoneyTransferDone() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.moneyTransferDone(operation);

        assertEquals(OperationState.PAID, operation.getState());
    }

    @Test
    void getCRYPTOSENDEDStateFromOperationAfterCryptoSendDone() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.cryptoSendDone(operation);

        assertEquals(OperationState.CRYPTOSENT, operation.getState());
    }

    @Test
    void get10WhenAskForPointsOnUsersFromOperationDoneInLess30Minutes() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(10, userService.findById(operation.getIntention().getUser().getId()).getPoints());
        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void get5WhenAskForPointsOnUsersFromOperationDoneInMore30Minutes() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operation.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(5, userService.findById(operation.getIntention().getUser().getId()).getPoints());
        assertEquals(5, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void modifyAnOperationWithPaidState() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, InvalidStateException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModifyDTO operationModifyDTO = new OperationModifyDTO(operation.getId(), OperationState.PAID, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModifyDTO);

        assertEquals(OperationState.PAID, operationService.findById(operation.getId()).getState());
    }

    @Test
    void modifyAnOperationWithCryptoSentState() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, InvalidStateException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModifyDTO operationModifyDTO = new OperationModifyDTO(operation.getId(), OperationState.CRYPTOSENT, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModifyDTO);

        assertEquals(OperationState.CRYPTOSENT, operationService.findById(operation.getId()).getState());
    }

    @Test
    void modifyAnOperationWithCancelledState() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, InvalidStateException, UserValidationException {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModifyDTO operationModifyDTO = new OperationModifyDTO(operation.getId(), OperationState.CANCELLED, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModifyDTO);

        assertEquals(OperationState.CANCELLED, operationService.findById(operation.getId()).getState());
    }

    @Test
    void whenTryToSetAStateInvalidThrowsException() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        int operationId = operationService.create(getSELLOperationRegister()).getId();

        assertThrows(InvalidStateException.class, () -> operationService.modify(new OperationModifyDTO(operationId, OperationState.ACTIVE, getUserWhoAcceptDB2Id())));
    }

    @Test
    void getPriceExceedVariationWithRespectIntentionSELLTypeLimitsPriceLowerThanQuoteOfCryptocurrency() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        int intentionId = getIntentionRegisterWithPrice320Units2DB().getId();

        assertThrows(PriceExceedVariationWithRespectIntentionTypeLimitsException.class, () -> operationService.create(new OperationRegisterDTO(intentionId, getUserWhoAcceptDB2Id())));
    }

    @Test
    void getPriceExceedVariationWithRespectIntentionBUYTypeLimitsPriceHigherThanQuoteOfCryptocurrency() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        int intentionId = getIntentionRegisterWithPrice330Units2DB().getId();

        assertThrows(PriceExceedVariationWithRespectIntentionTypeLimitsException.class, () -> operationService.create(new OperationRegisterDTO(intentionId, getUserWhoAcceptDB2Id())));
    }

    @Test
    void whenTryToTakeAnIntentionAlreadyTakenThrowsException() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(new CryptocurrencyRegisterDTO("DOGCOIN", 320.38));
        int intentionId = intentionService.create(new IntentionRegisterDTO(IntentionType.BUY,
                cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDBId())).getId();

        operationService.create(new OperationRegisterDTO(intentionId, getUserWhoAcceptDB2Id()));

        assertThrows(IntentionAlreadyTakenException.class, () -> operationService.create(new OperationRegisterDTO(intentionId, getUserWhoAcceptDB2Id())));
    }

    @Test
    void operationIsOnSetOperationOfUserWhoAcceptsAfterCryptoSentDone() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.cryptoSendDone(operation);

        assertTrue(userService.getFromDataBase(operation.getUserWhoAccepts().getId()).getOperations().stream().anyMatch(o -> o.getId() == operation.getId()));
    }

    @Test
    void operationViewClassIsObtainAfterOpenIsCalledWithAndOperationRegister() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        assertEquals(OperationViewDTO.class, operationService.open(getSomeOperationRegister()).getClass());
    }

    @Test
    void operationViewStringInfoObtainAfterOpenIsCalledWithAndOperationRegister() throws ResourceNotFoundException, PriceNotInAValidRangeException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, UserValidationException {
        assertEquals("OperationViewDTO(cryptocurrency=DAI, nominalAmount=961.14, quote=320.38, userWhoPostCompleteName=Paston Gaudio, operationNumber=0, reputation=0, sentAddress=Xwf5u5ef, actionToDo=Waiting for counterpart transfer)"
                , operationService.open(getSomeOperationRegister()).toString());
    }
}