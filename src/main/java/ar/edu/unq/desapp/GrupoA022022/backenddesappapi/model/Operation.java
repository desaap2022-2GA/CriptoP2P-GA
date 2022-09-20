package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.OperationState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "operation_des_CG")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "intention_id", referencedColumnName = "id")
    private Intention intention;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationState state = OperationState.ACTIVE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Intention getIntention() {
        return intention;
    }

    public void setIntention(Intention intention) {
        this.intention = intention;
    }

    public Operation(Intention intention) {
        this.intention = intention;
    }

    public OperationState getState() {
        return state;
    }

    public void setState(OperationState state) {
        this.state = state;
    }

    public Operation() {
    }

    public IntentionType type() {
        return this.intention.getType();
    }

    //CVU o BIlletera cripto segun tipo de OP
    public String transactionInfoToShow() {
        return this.intention.getTransactionInfoToShow();
    }

    //reputacion
    public int userReputation() {
        return this.intention.getUser().reputation();
    }
    //cambia el precio de la intencion por el de la cotizacion del momento
}