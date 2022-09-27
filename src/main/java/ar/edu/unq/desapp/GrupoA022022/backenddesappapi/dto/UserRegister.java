package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

public class UserRegister extends UserAPI {

    public UserRegister(String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String addressWalletActiveCripto) {
        super(name, lastname, email, address, password, mercadoPagoCVU, addressWalletActiveCripto);
    }

    public UserRegister() {
    }


}
