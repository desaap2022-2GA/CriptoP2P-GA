package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
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
@Table(name = "operation_des_CG")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    private long dateTime;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "intention_id", referencedColumnName = "id")
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
        return this.intention.getTransactionInfoToShow();
    }

    public int getUserReputation() {
        return this.intention.getUserReputation();
    }

    //cambia el precio de la intencion por el de la cotizacion del momento

    public String actionToDo(User user) {
        return ((this.intention.getType().equals(IntentionType.SELL) && Objects.equals(userWhoAccepts.getId(), user.getId()))
                || (this.intention.getType().equals(IntentionType.BUY) && !Objects.equals(userWhoAccepts.getId(), user.getId())))
                ? "Realice la transferencia" : "Confirmar recepcion";
    }

    public void cancelOperationByUser(User user) {
        this.setState(OperationState.CANCELLED);
        user.subPoints(20);
    }

    public void cancelOperationBySystem() {

        this.setState(OperationState.CANCELLED);
    }

    public void moneyTranferedDone() {

        this.setState(OperationState.PAID);
    }

    public void cryptoSendDone() {

        this.setState(OperationState.CRYPTOSENDED);
    }

    public void bonusTimeOperationAssign() {
        long thirtyminutesago = new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds();
        int points = (this.getDateTime() > thirtyminutesago) ? 10 : 5;
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

        return amount * dollarQuote;
    }
}