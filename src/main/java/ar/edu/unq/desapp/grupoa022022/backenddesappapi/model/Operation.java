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
import java.util.Set;

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
        this.userWhoAccepts = userWhoAccepts;
    }

    public IntentionType getType() {
        return this.intention.getType();
    }

    public String getTransactionInfoToShow() {
        return this.intention.transactionInfoToShow();
    }

    public int getUserReputation() {
        return this.intention.getUserReputation();
    }

    public String actionToDo(User user) {
        return ((this.intention.getType().equals(IntentionType.SELL) && Objects.equals(userWhoAccepts.getId(), user.getId()))
                || (this.intention.getType().equals(IntentionType.BUY) && !Objects.equals(userWhoAccepts.getId(), user.getId())))
                ? "Make transfer" : "Confirm reception";
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

    public double volumeTraded(Set<Operation> operations) {
        return operations.stream().mapToDouble(o -> o.getIntention().amountPriceInPesos()).sum();
    }

    public double amountInDollars(double amount, double dollarQuote) {
        return amount / dollarQuote;
    }

    public void addAnOperationToUsers() {
        this.userWhoAccepts.oneMoreOperation();
        this.intention.getUser().oneMoreOperation();
    }
}