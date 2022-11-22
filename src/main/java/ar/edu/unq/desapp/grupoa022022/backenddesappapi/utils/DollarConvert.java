package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.integration.ExternalProxyService;
import org.springframework.beans.factory.annotation.Autowired;

public class DollarConvert {

    @Autowired
    ExternalProxyService externalProxyService;

    public double amountInDollars(double amount) {
        double dollarQuote = externalProxyService.dolarSiLatestQuote();
        return amount / dollarQuote;
    }
}
