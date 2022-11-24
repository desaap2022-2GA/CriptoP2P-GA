package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.integration.ExternalProxyService;

public class DollarConvert {

    ExternalProxyService externalProxyService = new ExternalProxyService();

    public double amountInDollars(double amount) {
        double dollarQuote = externalProxyService.dolarSiLatestQuote();
        return amount / dollarQuote;
    }
}
