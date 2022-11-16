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

    int id;
    String dateTime;
    IntentionType type;
    String cryptocurrency;
    Double price;
    int units;
    Double amountPriceInPesos;
    UserView userWhoPost;
    boolean isTaken;

}
