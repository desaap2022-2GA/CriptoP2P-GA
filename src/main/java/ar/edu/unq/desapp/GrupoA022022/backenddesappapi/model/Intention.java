package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "intentionp2p_des_CG")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private long dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private IntentionType type;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    @NotNull
    @Min(value = 0)
    private Double price;

    @NotNull
    @Min(value = 0)
    private int units;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "intention")
    private Operation operation;

    @NotNull
    private boolean taken = false;

    public Intention(IntentionType type, Cryptocurrency cryptocurrency, Double price, int units, User user) {
        this.dateTime = new DateTimeInMilliseconds().currentTimeInMilliseconds;
        this.type = type;
        this.cryptocurrency = cryptocurrency;
        this.price = price;
        this.units = units;
        this.user = user;
        this.cryptocurrency.addIntention(this);
        this.user.addIntention(this);
    }

    public Intention() {
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public long getDateTime() {
        return dateTime;
    }

    public Double priceInPesos() {
        return this.price * this.units;
    }

    public Double amountToOperateInPesos() {
        return this.priceInPesos() * this.units;
    }

    public int numberOpUser() {
        return this.user.getNumberOperations();
    }

    public IntentionType getType() {
        return type;
    }

    public void setType(IntentionType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getTransactionInfoToShow() {
        return (this.type == IntentionType.SELL) ? this.user.getCVUMercadoPago()
                : this.user.getAdressWalletActiveCripto();
    }

    public int getUserReputation() {
        return this.user.reputation();
    }


}
