package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "cryptocurrencyp2p_des_CG")
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency")
    private Set<Quote> quotes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency")
    private Set<Intention> intentions = new HashSet<>();

    public Cryptocurrency(String name) {
        this.name = name;
    }

    public Cryptocurrency() {
    }

    public void setIntentions(Set<Intention> intentions) {
        this.intentions = intentions;
    }

    public Long getId() {
        return id;
    }

    public Set<Intention> getIntentions() {
        return intentions;
    }

    public Quote latestQuote() throws ResourceNotFoundException {
        if (!this.quotes.isEmpty()) {
            return this.quotes.stream()
                    .max(Comparator.comparing(Quote::getDateTime)).get();
        } else {
            throw new ResourceNotFoundException("does not exist quote for the cryptocurrency");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }

    public void addNewQuote(Quote quote) {
        this.quotes.add(quote);
    }

    public void addIntention(Intention intention) {
        this.intentions.add(intention);
    }

    public Set<Quote> last24HoursQuotes() {
        long nowMinusOneDay = new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds();
        return this.quotes.stream().filter(q -> q.getDateTime() > nowMinusOneDay).collect(Collectors.toSet());
    }
}
