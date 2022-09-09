package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;

import javax.persistence.*;

@Entity
@Table(name = "intentionp2p_desa")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long dateTime;

    @Enumerated(EnumType.STRING)
    private IntentionType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    private Double price;

    private int units;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Cryptocurrency getCrypto() {
        return cryptocurrency;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCrypto(Cryptocurrency crypto) {
        this.cryptocurrency = crypto;
    }

    public void assignCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTimeInMilliseconds) {
        this.dateTime = dateTimeInMilliseconds;
    }

    public Double priceInPesos() {
        return this.price * 148; //cambiar por cotizacion actualizada
    }

    public Double amountToOperateInPesos() {
        return this.priceInPesos() * this.units;
    }

    public int numberOpUser() {
        return this.user.getNumberOperations();
    }
}
