package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cryptocurrencyp2p")
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*lista de cotizaciones????*/

    public Set<Intention> getIntentions() {
        return intentions;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency")
    private Set<Intention> intentions = new HashSet<>();

    public Long getId() {
        return id;
    }
}
