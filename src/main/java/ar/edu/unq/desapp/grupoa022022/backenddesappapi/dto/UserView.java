package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.*;

@Getter
@Setter
public class UserView extends UserAPI{
    private int id;
    private int points;
    private int numberOperations;
    private int reputation;

    public UserView(int id, String name, String lastname, String email, String address, String CVUMercadoPago,
                    String addressWalletActiveCripto, int points, int numberOperations, int reputation) {
        super(name,
        lastname,
        email,
        address,
        CVUMercadoPago,
        addressWalletActiveCripto);
        this.id = id;
        this.points = points;
        this.numberOperations = numberOperations;
        this.reputation = reputation;
    }
}