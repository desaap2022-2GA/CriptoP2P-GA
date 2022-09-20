package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.Verify.*;

@Entity
@Table(name = "userp2p_des_CG")
/*@Data
@AllArgsConstructor(staticName = "build")
/*@NoArgsConstructor*/
@JsonFilter("UserDetails")
public class User {
    @Id
    //@GeneratedValue(generator = "User_ID_Generator", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "lastname must be between 3 and 30 characters")
    private String lastname;

    @Email(message = "lastname must be between 3 and 30 characters")
    private String email;

    @NotBlank
    @Size(min = 10, max = 30, message = "adress must be between 10 and 30 characters")
    private String adress;

    @NotBlank
    private String password;//investigar como validar los requerimentos indicados

    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must be 22 characters")
    private String CVUMercadoPago;

    @NotBlank
    @Size(min = 8, max = 8, message = "wallet must be 8 characters")
    private String adressWalletActiveCripto;

    private int points = 0;

    private int numberOperations = 0;
    //private String apiKey;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Intention> intentions = new HashSet<>();

    public User() {}

    public User(String name, String lastname, String email, String adress, String password, String CVUMercadoPago, String adressWalletActiveCripto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.CVUMercadoPago = CVUMercadoPago;
        this.adressWalletActiveCripto = adressWalletActiveCripto;
/*        this.points = 0;
        this.numberOperations = 0;
        this.intentions = new HashSet<>();*/
    }

    public Integer getId() {
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

    public String getPassword() {
        return password;
    }

    public String getCVUMercadoPago() {
        return CVUMercadoPago;
    }

    public String getAdressWalletActiveCripto() {
        return adressWalletActiveCripto;
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

    public void setCVUMercadoPago(String CVUMercadoPago) throws ExceptionsUser {
        if (verifyCVUMercadoPago(CVUMercadoPago)) {
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

    public int reputation() {
        return (this.numberOperations != 0) ? this.points / this.numberOperations : 0;
    }

    public void addIntention(Intention intention) {
        this.intentions.add(intention);
    }

    public Set<Intention> getIntentions() {
        return intentions;
    }
}