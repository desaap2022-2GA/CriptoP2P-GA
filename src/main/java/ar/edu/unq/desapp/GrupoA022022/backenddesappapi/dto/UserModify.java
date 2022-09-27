package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

public class UserModify extends UserAPI {

    private int id;

    public UserModify(int id, String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String addressWalletActiveCripto) {
        super(name, lastname, email, address, password, mercadoPagoCVU, addressWalletActiveCripto);
        this.id = id;
    }

    public UserModify() {
    }

    public int getId() {
        return id;
    }
}
