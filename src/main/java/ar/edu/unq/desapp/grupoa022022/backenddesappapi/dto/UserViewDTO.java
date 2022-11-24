package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.*;

@Getter
@Setter
public class UserViewDTO extends UserAPIDTO {
    private int id;
    private int points;
    private int numberOperations;
    private int reputation;

    public UserViewDTO(int id, String name, String lastname, String email, String address, String mercadoPagoCVU,
                       String addressWalletActiveCrypto, int points, int numberOperations, int reputation) {
        super(name, lastname, email, address, mercadoPagoCVU, addressWalletActiveCrypto);
        this.id = id;
        this.points = points;
        this.numberOperations = numberOperations;
        this.reputation = reputation;
    }
}