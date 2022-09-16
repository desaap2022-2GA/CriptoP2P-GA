package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.management.ConstructorParameters;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.Verify.*;

@Entity
@Table (name = "userp2p_desa")

@ConstructorBinding()
public class User {

    @Id
    //@GeneratedValue(generator = "User_ID_Generator", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min=3, max =30)
    private String name;
    //@Column
    @NotBlank
    @Size(min=3, max =30)
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
    private int points;

    @Column
    private float reputation;

    @Column
    private int numberOperation;

    //@Column
    private int numberOperations;
    //private String apiKey;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Intention> intentions = new HashSet<>();

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
        this.points = 0;
        this.reputation = 0;
        this.numberOperations = 0;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) throws ExceptionsUser {
        if (verifyLong(name, 3, 30)) {
            this.name = name;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) throws ExceptionsUser {
        if (verifyLong(lastname, 3, 30)) {
            this.lastname = lastname;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }

    public String getEmail() { return email; }

    public void setEmail(String email) throws ExceptionsUser {
        if (verifyEmail(email)) {
            this.email = email;
        } else {
            throw new ExceptionsUser("Campo Obligatoiro. Debe tener formato de email");
        }
    }

    public String getAdress() { return adress; }

    public void setAdress(String adress) throws ExceptionsUser {
        if (verifyLong(adress, 10, 30)) {
            this.adress = adress;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 10 y 30 caracteres");
        }
    }

    public String getPassword() { return password;  }

    public void setPassword(String password) throws ExceptionsUser {
        if (verifyPassword(password)) {
            this.password = password;
        } else {
            throw new ExceptionsUser("Debe contener al menos 1 minúscula, 1 mayúscula, " +
                    "1 carácter especial y como mínimo 6 caracteres");
        }
    }

    public String getCVUMercadoPago() { return CVUMercadoPago; }

    public void setCVUMercadoPago (String CVUMercadoPago) throws ExceptionsUser {
        if(verifyCVUMercadoPago(CVUMercadoPago)){
            this.CVUMercadoPago = CVUMercadoPago;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 22 dígitos");
        }
    }


    public String getAdressWalletActiveCripto() { return adressWalletActiveCripto;
    }

    public void setAdressWalletActiveCripto(String adressWalletActiveCripto) throws ExceptionsUser {
        if (verifyAdressWalletActiveCripto(adressWalletActiveCripto)) {
            this.adressWalletActiveCripto = adressWalletActiveCripto;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 8 dígitos");
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumberOperations() {
        return numberOperations;
    }

    public void setNumberOperations(int numberOperations) {
        this.numberOperations = numberOperations;
    }

    public float getReputation(){ return reputation;}

    public void setReputation(){
        this.reputation = calculateReputation();
    }


    public int calculateReputation(){
        return (this.numberOperations != 0) ? Math.round(this.points / this.numberOperations) : 0;
    }
}