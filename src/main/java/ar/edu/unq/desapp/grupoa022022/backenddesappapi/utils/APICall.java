package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.Casa;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ObjectCasa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class APICall {

    RestTemplate restTemplate = new RestTemplate();

    public double dolarSiCall() {

        String url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        ResponseEntity<ObjectCasa[]> objetosCasa =
                restTemplate.getForEntity(url, ObjectCasa[].class);

        Casa responseBean = objetosCasa.getBody()[1].getCasa();
        String quoteDollarBlueSale = responseBean.getVenta().replaceAll(",", ".");
        ;
        System.out.println("conversion de string a float " + Float.parseFloat(quoteDollarBlueSale));
        return Double.parseDouble(quoteDollarBlueSale);
    }
}