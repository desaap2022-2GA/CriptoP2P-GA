package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    private long dateTime;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "intention_id", referencedColumnName = "id")
    @JsonManagedReference
    private Intention intention;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationState state = OperationState.ACTIVE;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userWhoAccepts_id", referencedColumnName = "id")
    private User userWhoAccepts;

    public Operation(Intention intention, User userWhoAccepts) {
        this.dateTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        this.intention = intention;
        intention.setOperation(this);
        this.userWhoAccepts = userWhoAccepts;
        userWhoAccepts.addOperation(this);
    }

    public String actionToDo(User user) {

        String actionMessage = "";

        switch (this.getState()) {
            case CANCELLED -> actionMessage = "Cancelled";
            case CRYPTOSENT -> actionMessage = (this.conditionToShowActionMessage(user))
                    ? "Confirm reception on Wallet, Transaction finished" : "Waiting for counterpart confirm reception on Wallet, " +
                    "Transaction finished";
            case PAID -> actionMessage = (this.conditionToShowActionMessage(user))
                    ? "Waiting for counterpart confirm reception and transfer cryptocurrency" : "Confirm reception on " +
                    "Mercado Pago and transfer Cryptocurrency";
            case ACTIVE -> actionMessage = (this.conditionToShowActionMessage(user))
                    ? "Make transfer" : "Waiting for counterpart transfer";
        }
        return actionMessage;
    }

    public boolean conditionToShowActionMessage(User user) {
        return (this.intention.getType().equals(IntentionType.SELL) && Objects.equals(userWhoAccepts.getId(), user.getId()))
                || (this.intention.getType().equals(IntentionType.BUY) && !Objects.equals(userWhoAccepts.getId(), user.getId()));
    }

    public void cancelOperationByUser(User user) {
        this.setState(OperationState.CANCELLED);
        user.subPoints(20);
    }

    public void cancelOperationBySystem() {
        this.setState(OperationState.CANCELLED);
    }

    public void moneyTransferredDone() {
        this.setState(OperationState.PAID);
    }

    public void cryptoSendDone() {
        this.setState(OperationState.CRYPTOSENT);
    }

    public void bonusTimeOperationAssign() {
        long thirtyMinutesAgo = new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds();
        int points = (this.getDateTime() > thirtyMinutesAgo) ? 10 : 5;
        addPointsToUsers(points);
    }

    private void addPointsToUsers(int points) {
        this.userWhoAccepts.addPoints(points);
        this.intention.getUser().addPoints(points);
    }

    public void addAnOperationToUsers() {
        this.userWhoAccepts.oneMoreOperation();
        this.intention.getUser().oneMoreOperation();
    }
}