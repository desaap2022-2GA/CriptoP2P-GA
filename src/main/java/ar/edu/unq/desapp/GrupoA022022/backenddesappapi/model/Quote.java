package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quote_des_cg")
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

    public Quote() {
    }

    public Quote(Cryptocurrency cryptocurrency, Double price) {
        this.dateTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        this.cryptocurrency = cryptocurrency;
        this.price = price;
        this.cryptocurrency.addNewQuote(this);
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public double fivePercentDown() {
        return this.price * 0.95;
    }

    public double fivePercentUp() {
        return this.price * 1.05;
    }

    public boolean intentionPriceInARangeOfFiveUpAndDown(Double intentionPrice) {
        return fivePercentDown() < intentionPrice && intentionPrice < fivePercentUp();
    }

    public boolean intentionPriceMoreThanQuotePrice(Double intentionPrice) {
        return intentionPrice > this.price;
    }

    public boolean intentionPriceLessThanQuotePrice(Double intentionPrice) {
        return intentionPrice < this.price;
    }

    public int getId() {
        return id;
    }
}