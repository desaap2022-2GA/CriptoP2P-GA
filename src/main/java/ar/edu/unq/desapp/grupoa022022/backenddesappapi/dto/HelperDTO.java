package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

public class HelperDTO {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User userRegisterToUser(UserRegister userRegister) {
        return new User(userRegister.getName(), userRegister.getLastname(), userRegister.getEmail()
                , userRegister.getAddress(), encoder.encode(userRegister.getPassword()), userRegister.getMercadoPagoCVU()
                , userRegister.getAddressWalletActiveCrypto());
    }
/*
    public User userModifyToUser(UserModify userModify, User userOriginal) throws ExceptionsUser {

        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getName(), userOriginal.getName())) {
            userOriginal.setName(userModify.getName());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getLastname(), userOriginal.getLastname())) {
            userOriginal.setLastname(userModify.getLastname());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getEmail(), userOriginal.getEmail())) {
            userOriginal.setEmail(userModify.getEmail());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getAddress(), userOriginal.getAddress())) {
            userOriginal.setAddress(userModify.getAddress());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getPassword(), userOriginal.getPassword())) {
            userOriginal.setPassword(encoder.encode(userModify.getPassword()));
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getMercadoPagoCVU(), userOriginal.getMercadoPagoCVU())) {
            userOriginal.setMercadoPagoCVU(userModify.getMercadoPagoCVU());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getAddressWalletActiveCrypto(), userOriginal.getaddressWalletActiveCrypto())) {
            userOriginal.setaddressWalletActiveCrypto(userModify.getAddressWalletActiveCrypto());
        }
        return userOriginal;
    }

 */

    public boolean firstNotNullAndFirstAndSecondNotEquals(String firstCheck, String secondCheck) {
        return firstCheck != null && !Objects.equals(firstCheck, secondCheck);
    }

    public UserView userToUserView(User user) {
        return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getAddress(),
                user.getMercadoPagoCVU(), user.getAddressWalletActiveCrypto(), user.getPoints(),
                user.getNumberOperations(), user.getReputation());
    }

    public List<UserView> usersToUsersView(List<User> all) {
        return all.stream().map(this::userToUserView).toList();
    }

    public CryptoDetails intentionToCryptoDetails(Intention intention) throws ResourceNotFound {
        return new CryptoDetails(intention.getCryptocurrency().getName(), intention.getUnits()
                , intention.getCryptocurrency().latestQuote().getPrice(), intention.amountPriceInPesos());
    }

    public OperationView operationToOperationView(Operation operation, User userWhoAsk) throws ResourceNotFound {
        Intention intention = operation.getIntention();
        Cryptocurrency cryptocurrency = intention.getCryptocurrency();
        User user = intention.getUser();
        String completeName = user.getName() + " " + user.getLastname();
        return new OperationView(intention.getCryptocurrency().getName(), intention.actualAmountPriceInPesos()
                , cryptocurrency.latestQuote().getPrice(), completeName, user.getNumberOperations(), user.getReputation()
                , intention.transactionInfoToShow(userWhoAsk), operation.actionToDo(userWhoAsk));
    }

    public IntentionView intentionToIntentionView(Intention intention, User userWhoPost){
        return new IntentionView(String.valueOf(intention.getId()), new DateTimeInMilliseconds().convertLongToDate(intention.getDateTime())
                , intention.getType().toString(), intention.getCryptocurrency().getName(), intention.getPrice().toString(), String.valueOf(intention.getUnits())
                , intention.amountPriceInPesos().toString(), userWhoPost.getName()+" "+userWhoPost.getLastname());
    }

    public ActiveIntentionView intentionToActiveIntentionView(Intention intention, User userWhoPost){
        return new ActiveIntentionView(String.valueOf(intention.getId()), new DateTimeInMilliseconds().convertLongToDate(intention.getDateTime())
                , intention.getType().toString(), intention.getCryptocurrency().getName(), intention.getPrice().toString(), String.valueOf(intention.getUnits())
                , intention.amountPriceInPesos().toString(), userWhoPost.getName()+" "+userWhoPost.getLastname(), String.valueOf(userWhoPost.getNumberOperations()), String.valueOf(userWhoPost.getReputation()));
    }
    public User userModify(User user, String field, String data) throws ExceptionsUser {
        switch (field){
            case "name": user.setName(data);
                break;
            case "lastname": user.setLastname(data);
                break;
            case "email": user.setEmail(data);
                break;
            case "address": user.setAddress(data);
                break;
            case "password": user.setPassword(data);
                break;
            case "mercadoPagoCVU": user.setMercadoPagoCVU(data);
                break;
            case "addressWalletActiveCripto": user.setAddressWalletActiveCrypto(data);
                break;
        }
        return user;
    }
}