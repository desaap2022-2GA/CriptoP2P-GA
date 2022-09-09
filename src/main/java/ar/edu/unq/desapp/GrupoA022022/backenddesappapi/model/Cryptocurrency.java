package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cryptocurrencyp2p_desa")
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency")
    private Set<Quote> quotes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency")
    private Set<Intention> intentions = new HashSet<>();

    public void setIntentions(Set<Intention> intentions) {
        this.intentions = intentions;
    }

    public Long getId() {
        return id;
    }

    public Set<Intention> getIntentions() {
        return intentions;
    }

    public Quote latestQuote() throws Exception {
        if (!this.quotes.isEmpty()) {
            return this.quotes.stream().findFirst().get();
        } else {
            throw new Exception();
        }
    }
}
