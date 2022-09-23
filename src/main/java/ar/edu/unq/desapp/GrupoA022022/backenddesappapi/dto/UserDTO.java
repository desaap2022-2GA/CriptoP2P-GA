package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

public class UserDTO {

    private String name;

    private String lastname;

    private String email;

    private String adress;

    private String password;

    private String CVUMercadoPago;

    private String adressWalletActiveCripto;

    public UserDTO(String name, String lastname, String email, String adress, String password, String CVUMercadoPago, String adressWalletActiveCripto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.CVUMercadoPago = CVUMercadoPago;
        this.adressWalletActiveCripto = adressWalletActiveCripto;
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

    public String getCVUMercadoPago() {
        return CVUMercadoPago;
    }

    public String getAdressWalletActiveCripto() {
        return adressWalletActiveCripto;
    }

}
