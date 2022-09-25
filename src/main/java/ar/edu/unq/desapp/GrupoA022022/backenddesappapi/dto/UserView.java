package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

public class UserView {
    private int id;

    private String name;

    private String lastname;

    private String email;

    private String adress;

    private String CVUMercadoPago;

    private String adressWalletActiveCripto;

    public UserView(int id, String name, String lastname, String email, String adress, String CVUMercadoPago, String adressWalletActiveCripto) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.CVUMercadoPago = CVUMercadoPago;
        this.adressWalletActiveCripto = adressWalletActiveCripto;
    }

    public UserView() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getCVUMercadoPago() {
        return CVUMercadoPago;
    }

    public String getAdressWalletActiveCripto() {
        return adressWalletActiveCripto;
    }
}