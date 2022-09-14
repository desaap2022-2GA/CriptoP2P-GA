package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long dateTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    @NotNull
    private Double price;

    public Quote() {
    }

    public Quote(Cryptocurrency cryptocurrency, Double price) {
        this.dateTime = new DateTimeInMilliseconds().currentTimeInMilliseconds;
        this.cryptocurrency = cryptocurrency;
        this.price = price;
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

}