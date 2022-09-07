package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import javax.persistence.*;
import static ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Verify.*;

@Entity
@Table (name = "userp2p")
public class User {

    @Id
    //@GeneratedValue(generator = "User_ID_Generator", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
        this.name = "";
        this.lastname = "";
        this.email = "";
        this.adress = "";
        this.password = "";
        this.CVUMercadoPago = "";
        this.adressWalletActiveCripto = "";
    }

    public User(String name, String lastname, String email, String adress, String password, String CVUMercadoPago, String adressWalletActiveCripto){
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

    public void setName(String name) throws ExceptionsUser {
        if (verifyLong(name, 3, 30)) {
            this.name = name;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }
    public void setLastname(String lastname) throws ExceptionsUser {
        if (verifyLong(lastname, 3, 30)) {
            this.lastname = lastname;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }
    public void setAdress(String adress) throws ExceptionsUser {
        if (verifyLong(adress, 10, 30)) {
            this.adress = adress;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 10 y 30 caracteres");
        }
    }
    public void setEmail(String email) throws ExceptionsUser {
        if (verifyEmail(email)) {
            this.email = email;
        } else {
            throw new ExceptionsUser("Campo Obligatoiro. Debe tener formato de email");
        }
    }
   public void setPassword(String password) throws ExceptionsUser {
        if (verifyPassword(password)) {
            this.password = password;
        } else {
            throw new ExceptionsUser("Debe contener al menos 1 minúscula, 1 mayúscula, " +
                    "1 carácter especial y como mínimo 6 caracteres");
        }
    }
    public void setCVUMercadoPago (String CVUMercadoPago) throws ExceptionsUser {
        if(verifyCVUMercadoPago(CVUMercadoPago)){
            this.CVUMercadoPago = CVUMercadoPago;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 22 dígitos");
        }
    }

    public void setAdressWalletActiveCripto(String adressWalletActiveCripto) throws ExceptionsUser {
        if (verifyAdressWalletActiveCripto(adressWalletActiveCripto)) {
            this.adressWalletActiveCripto = adressWalletActiveCripto;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 8 dígitos");
        }
    }
}