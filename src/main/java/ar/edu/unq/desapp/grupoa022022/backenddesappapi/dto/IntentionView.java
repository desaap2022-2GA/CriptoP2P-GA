package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntentionView {

    String id;
    String dateTime;
    String operationType;
    String cryptocurrency;
    String price;
    String units;
    String amountPriceInPesos;
    String userWhoPost;
}