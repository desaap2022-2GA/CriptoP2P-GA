package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {
    private int id;

    private String name;

    private String lastname;

    private String email;

    private String address;

    private String CVUMercadoPago;

    private String addressWalletActiveCripto;

    public UserView(int id, String name, String lastname, String email, String address, String CVUMercadoPago, String addressWalletActiveCripto) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.CVUMercadoPago = CVUMercadoPago;
        this.addressWalletActiveCripto = addressWalletActiveCripto;
    }

    public UserView() {
    }
}