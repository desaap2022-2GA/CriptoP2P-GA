package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class ActiveIntentionView {

    String id;
    String dateTime;
    String operationType;
    String cryptocurrency;
    String price;
    String units;
    String amountPriceInPesos;
    String userWhoPost;
    String operationsNumbers;
    String reputation;

    public ActiveIntentionView(String id, String dateTime, String operationType, String cryptocurrency, String price,
                               String units, String amountPriceInPesos, String userWhoPost, String operationsNumbers,
                               String reputation) {
        this.id = id;
        this.dateTime = dateTime;
        this.operationType = operationType;
        this.cryptocurrency = cryptocurrency;
        this.price = price;
        this.units = units;
        this.amountPriceInPesos = amountPriceInPesos;
        this.userWhoPost = userWhoPost;
        this.operationsNumbers = operationsNumbers;
        if (Objects.equals(reputation, "0")) {
            this.reputation = "Sin operaciones";
        } else {
            this.reputation = reputation;
        }
    }
}