package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserModify {

    private int id;
    @NotBlank
    @Size(min = 3, max = 30, message = "Name: must be between 3 and 30 characters")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "Lastname: must be between 3 and 30 characters")
    private String lastname;
    @Email(message = "Email: must be a properly formatted email address")
    private String email;
    @NotBlank(message = "Address: it can not be null")
    private String address;
    @NotBlank(message = "Password: it can not be null")
    private String password;
    @NotBlank
    @Size(min = 22, max = 22, message = "MercadoPagoCVU: must be 22 characters")
    private String mercadoPagoCVU;
    @NotBlank
    @Size(min = 8, max = 8, message = "AdressWalleActiveCryptot: must be 8 characters")
    private String addressWalletActiveCripto;

    public UserModify(int id, String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String adressWalletActiveCripto) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.addressWalletActiveCripto = adressWalletActiveCripto;
    }
    public UserModify(){}

    public int getId() {
        return id;
    }

    public String getMercadoPagoCVU() {
        return mercadoPagoCVU;
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

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getAddressWalletActiveCripto() {
        return addressWalletActiveCripto;
    }

}
