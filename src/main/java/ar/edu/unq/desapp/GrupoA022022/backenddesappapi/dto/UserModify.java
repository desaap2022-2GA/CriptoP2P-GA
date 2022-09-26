package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

public class UserModify {

    private int id;

    private String name;

    private String lastname;

    private String email;

    private String adress;

    private String password;

    private String cvumercadoPago;

    private String adressWalletActiveCripto;

    public UserModify(String name, String lastname, String email, String adress, String password, String cvumercadoPago, String adressWalletActiveCripto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.cvumercadoPago = cvumercadoPago;
        this.adressWalletActiveCripto = adressWalletActiveCripto;
    }
    public UserModify(){}

    public int getId() {
        return id;
    }

    public String getCvumercadoPago() {
        return cvumercadoPago;
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

    public String getPassword() {
        return password;
    }

    public String getAdressWalletActiveCripto() {
        return adressWalletActiveCripto;
    }

}
