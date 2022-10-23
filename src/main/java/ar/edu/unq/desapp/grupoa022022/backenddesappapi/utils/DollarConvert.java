package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

public class DollarConvert {

    public double amountInDollars(double amount) {
        double dollarQuote = 200.00;
        return amount / dollarQuote;
    }
}
