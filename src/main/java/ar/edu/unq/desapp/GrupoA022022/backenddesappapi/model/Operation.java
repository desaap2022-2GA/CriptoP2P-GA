package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.OperationState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "operation_des_CG")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private long dateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "intention_id", referencedColumnName = "id")
    private Intention intention;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationState state = OperationState.ACTIVE;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoAccepts_id", referencedColumnName = "id")
    private User userWhoAccepts;

    public Long getId() {
        return id;
    }

    public Intention getIntention() {
        return intention;
    }

    public void setIntention(Intention intention) {
        this.intention = intention;
    }

    public Operation(Intention intention) {
        this.dateTime = new DateTimeInMilliseconds().currentTimeInMilliseconds;
        this.intention = intention;
    }

    public Operation() {
    }

    public OperationState getState() {
        return state;
    }

    public void setState(OperationState state) {
        this.state = state;
    }

    public IntentionType getType() {
        return this.intention.getType();
    }

    //CVU o BIlletera cripto segun tipo de OP
    public String getTransactionInfoToShow() {
        return this.intention.getTransactionInfoToShow();
    }

    //reputacion
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
        long thirtyminutesago = new DateTimeInMilliseconds().currentTimeMinus30MinutesInMilliseconds;
        int points = (this.getDateTime() > thirtyminutesago) ? 10 : 5;
        addPointsToUsers(points);
    }

    private void addPointsToUsers(int points) {
        this.userWhoAccepts.addPoints(points);
        this.intention.getUser().addPoints(points);
    }

    public long getDateTime() {
        return dateTime;
    }
}