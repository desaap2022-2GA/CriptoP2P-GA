package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Exceptions.ExceptionsLastname;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Exceptions.ExceptionsName;

import static ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Model.Verify.*;

public class User {

    //@Id
    //@GeneratedValue(generator = "User_ID_Generator", initialValue = 1)
    private Integer id;
    //@Column
    private String name;
    //@Column
    private String lastname;
   // @Column
    private String email;
    //@Column
    private String adress;
    //@Column
    private String password;
    //@Column
    private String CVUMercadoPago;
    //@Column
    private String adressWalletActiveCripto;
    //@Column
    //private String apiKey;

    public User(){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.CVUMercadoPago = CVUMercadoPago;
        this.adressWalletActiveCripto = adressWalletActiveCripto;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getLastname() { return lastname; }

    public String getEmail() { return email; }

    public String getAdress() { return adress; }

    public String getPassword() { return password;  }

    public String getCVUMercadoPago() { return CVUMercadoPago; }

    public String getAdressWalletActiveCripto() { return adressWalletActiveCripto;
    }



    public void setName(String name){
        if (verifyLong(name, 3, 30)) {
            this.name = name;
        } else {
            new ExceptionsName();
        }
    }

    public void setLastname(String lastname){
        if (verifyLong(lastname, 3, 30)) {
            this.lastname = lastname;
        } else {
            new ExceptionsLastname();
        }
    }

    public void setAdress(String adress){
        if (verifyLong(adress, 10, 30)) {
            this.adress = adress;
        } else {}
    }

    public void setEmail(String email){
        if (verifyEmail(email)) {
            this.email = email;
        } else {}
    }

    public void setPassword(String password){
        if (verifyPassword(password)) {
            this.password = password;
        } else {}
    }

    public void setCVUMercadoPago (String CVUMercadoPago){
        if(verifyCVUMercadoPago(CVUMercadoPago)){
            this.CVUMercadoPago = CVUMercadoPago;
        } else {}
    }

    public void setAdressWalletActiveCripto(String adressWalletActiveCripto){
        if (verifyCVUMercadoPago(adressWalletActiveCripto)) {
            this.adressWalletActiveCripto = adressWalletActiveCripto;
        } else {}
    }
}




