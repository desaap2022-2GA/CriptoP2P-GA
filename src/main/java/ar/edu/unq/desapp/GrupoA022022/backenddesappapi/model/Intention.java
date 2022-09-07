package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import javax.persistence.*;

@Entity
@Table(name = "intentionp2p")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cryptocurrency_id", referencedColumnName = "id")
    private Cryptocurrency cryptocurrency;

    private Long price;

    private Long units;

    public Cryptocurrency getCrypto() {
        return cryptocurrency;
    }

    public void setCrypto(Cryptocurrency crypto) {
        this.cryptocurrency = crypto;
    }


    public void assignCryptocurrency(Cryptocurrency cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }
}
