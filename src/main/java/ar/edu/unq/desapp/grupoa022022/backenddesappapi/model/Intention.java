package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "intentionp2p_des_CG")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private long dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private IntentionType type;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    @NotNull
    @Min(value = 0)
    private Double price;

    @NotNull
    @Min(value = 0)
    private int units;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "intention")
    @JsonBackReference
    private Operation operation;

    @NotNull
    private boolean taken = false;

    public Intention(IntentionType type, Cryptocurrency cryptocurrency, Double price, int units, User user) {
        this.dateTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        this.type = type;
        this.cryptocurrency = cryptocurrency;
        this.price = price;
        this.units = units;
        this.user = user;
        this.cryptocurrency.addIntention(this);
        this.user.addIntention(this);
    }

    public Double amountPriceInPesos() {
        return this.price * this.units;
    }

    public Double amountPriceInDollars(Double dollarPrice) {
        return this.amountPriceInPesos() / dollarPrice;
    }

    public int numberOperationsUser() {
        return this.user.getNumberOperations();
    }

    public int getUserReputation() {
        return this.user.getReputation();
    }

    public String transactionInfoToShow() {
        return (this.type == IntentionType.SELL) ? this.user.getMercadoPagoCVU()
                : this.user.getAddressWalletActiveCripto();
    }
}