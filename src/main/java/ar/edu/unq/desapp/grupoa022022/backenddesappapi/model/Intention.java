package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "intentions")
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

    @OneToOne(mappedBy = "intention", cascade = CascadeType.MERGE)
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

    public Double actualAmountPriceInPesos() throws ResourceNotFound {
        return this.getCryptocurrency().latestQuote().getPrice() * this.units;
    }

    public Double actualAmountPriceInDollars(Double dollarPrice) throws ResourceNotFound {
        return this.actualAmountPriceInPesos() / dollarPrice;
    }

    public String transactionInfoToShow(User userWhoAsk) {

        String infoMessage = "";

        switch (this.getType()) {
            case SELL -> infoMessage = (this.conditionToShowInfoMessage(userWhoAsk))
                    ? this.operation.getUserWhoAccepts().getaddressWalletActiveCrypto() : this.user.getMercadoPagoCVU();
            case BUY -> infoMessage = (this.conditionToShowInfoMessage(userWhoAsk))
                    ? this.operation.getUserWhoAccepts().getMercadoPagoCVU() : this.user.getaddressWalletActiveCrypto();
        }
        return infoMessage;
    }

    public boolean conditionToShowInfoMessage(User userWhoAsk) {
        return Objects.equals(this.user, userWhoAsk);
    }

    public Cryptocurrency getCryptocurrency(){ return this.cryptocurrency;}
}