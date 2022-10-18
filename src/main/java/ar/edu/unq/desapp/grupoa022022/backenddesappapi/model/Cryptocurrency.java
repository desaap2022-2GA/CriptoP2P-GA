package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cryptocurrencyp2p_des_CG")
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency", fetch = FetchType.EAGER)

    private Set<Quote> quotes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cryptocurrency", fetch = FetchType.EAGER)
    private Set<Intention> intentions = new HashSet<>();

    public Cryptocurrency(String name) {
        this.name = name;
    }

    public Quote latestQuote() throws ResourceNotFound {
        Optional<Quote> quote = this.quotes.stream().reduce((q1, q2) -> q1.getDateTime() > q2.getDateTime() ? q1 : q2);
        if (quote.isPresent()) {
            return quote.get();
        } else {
            throw new ResourceNotFound("does not exists quote for cryptocurrency");
        }
    }

    public void addNewQuote(Quote quote) {
        this.quotes.add(quote);
    }

    public void addIntention(Intention intention) {
        this.intentions.add(intention);
    }

    public void removeIntention(Intention intention) { this.intentions.remove(intention);}
    public Set<Quote> last24HoursQuotes() {
        long nowMinusOneDay = new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds();
        return this.quotes.stream().filter(q -> q.getDateTime() > nowMinusOneDay).collect(Collectors.toSet());
    }
}