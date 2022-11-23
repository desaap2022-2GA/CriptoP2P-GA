package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class TradedBetweenDates {

    private String date;
    private double dollarAmount;
    private double pesosAmount;
    Set<CryptoDetails> cryptoDetails = new HashSet<>();

    public TradedBetweenDates(double dollarAmount, double pesosAmount){
        this.date = new DateTimeInMilliseconds().getCurrentTimeInMillisecondsDateFormat();
        this.dollarAmount = dollarAmount;
        this.pesosAmount = pesosAmount;
    }

    public void addCryptoDetails(CryptoDetails cryptoDetails){
        this.cryptoDetails.add(cryptoDetails);
    }
}
