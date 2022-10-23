package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TradedBetweenDates {

    private long date;
    private double dollarAmount;
    private double pesosAmount;
    Set<CryptoDetails> cryptoDetails = new HashSet<>();

    public TradedBetweenDates(double dollarAmount, double pesosAmount){
        this.date = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        this.dollarAmount = dollarAmount;
        this.pesosAmount = pesosAmount;
    }

    public void addCryptoDetails(CryptoDetails cryptoDetails){
        this.cryptoDetails.add(cryptoDetails);
    }
}
