package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.Casa;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ObjectCasa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class APICall {

    RestTemplate restTemplate = new RestTemplate();

    public double dolarSiCall() {

        String url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        ResponseEntity<ObjectCasa[]> objetcsCasa =
                restTemplate.getForEntity(url, ObjectCasa[].class);
        Casa responseBean = Objects.requireNonNull(objetcsCasa.getBody())[1].getCasa();
        String quoteDollarBlueSale = responseBean.getVenta().replace(",", ".");
        return Double.parseDouble(quoteDollarBlueSale);
    }
}