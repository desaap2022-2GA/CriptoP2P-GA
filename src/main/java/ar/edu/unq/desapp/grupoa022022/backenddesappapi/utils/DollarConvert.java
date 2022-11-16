package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

public class DollarConvert {

    public double amountInDollars(double amount) {
        double dollarQuote = new APICall().dolarSiLatestQuote();
        return amount / dollarQuote;
    }
}
