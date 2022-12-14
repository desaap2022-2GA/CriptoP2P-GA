package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
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
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long dateTime;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    @NotNull
    @Min(value = 0)
    private Double price;

    public Quote(Cryptocurrency cryptocurrency, Double price) {
        this.dateTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        this.cryptocurrency = cryptocurrency;
        this.price = price;
        this.cryptocurrency.addNewQuote(this);
    }

    public double fivePercentDown() {
        return this.price * 0.95;
    }

    public double fivePercentUp() {
        return this.price * 1.05;
    }

    public boolean intentionPriceInARangeOfFiveUpAndDown(Double intentionPrice) {
        return fivePercentDown() <= intentionPrice && intentionPrice <= fivePercentUp();
    }

    public boolean intentionPriceHigherThanQuotePrice(Double intentionPrice) {

        return intentionPrice > this.price;
    }

    public boolean intentionPriceLowerThanQuotePrice(Double intentionPrice) {

        return intentionPrice < this.price;
    }

    public boolean priceExceedVariationWithRespectTheIntentionPriceAccordingIntentionTypeLimits(Double intentionPrice
            , IntentionType intentionType) {
        if (intentionType.equals(IntentionType.SELL)) {
            return intentionPriceLowerThanQuotePrice(intentionPrice);
        } else {
            return intentionPriceHigherThanQuotePrice(intentionPrice);
        }
    }
}