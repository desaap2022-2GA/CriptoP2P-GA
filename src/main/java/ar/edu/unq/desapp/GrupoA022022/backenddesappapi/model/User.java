package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.OperationState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.Verify.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "desappcriptp2p_10")
/*@ConstructorBinding()*/
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "lastname must be between 3 and 30 characters")
    private String lastname;

    private String email;

    private String address;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must be 22 characters")
    private String mercadoPagoCVU;

    @NotBlank
    @Size(min = 8, max = 8, message = "wallet must be 8 characters")
    private String addressWalletActiveCripto;

    private int points = 0;

    private int numberOperations = 0;

    //private String apiKey;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Intention> intentions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userWhoAccepts")
    private Set<Operation> operations = new HashSet<>();

    public User(String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String addressWalletActiveCripto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.addressWalletActiveCripto = addressWalletActiveCripto;
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

    public void setEmail(String email) throws ExceptionsUser {
        if (verifyEmail(email)) {
            this.email = email;
        } else {
            throw new ExceptionsUser("Campo Obligatoiro. Debe tener formato de email");
        }
    }

    public void setAddress(String address) throws ExceptionsUser {
        if (verifyLong(address, 10, 30)) {
            this.address = address;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe tener entre 10 y 30 caracteres");
        }
    }

    public void setPassword(String password) throws ExceptionsUser {
        if (verifyPassword(password)) {
            this.password = password;
        } else {
            throw new ExceptionsUser("Password: Must contain at least 1 lowercase, 1 uppercase, " +
                    "1 special character, and at least 6 characters");
        }
    }

    public void setMercadoPagoCVU(String CVUMercadoPago) throws ExceptionsUser {
        if (verifyCVUMercadoPago(CVUMercadoPago)) {
            this.mercadoPagoCVU = CVUMercadoPago;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 22 dígitos");
        }
    }

    public String getAddressWalletActiveCripto() {
        return addressWalletActiveCripto;
    }

    public void setAddressWalletActiveCripto(String adressWalletActiveCripto) throws ExceptionsUser {
        if (verifyAdressWalletActiveCripto(adressWalletActiveCripto)) {
            this.addressWalletActiveCripto = adressWalletActiveCripto;
        } else {
            throw new ExceptionsUser("Campo Obligatorio. Debe contener 8 dígitos");
        }
    }

    public int getReputation() {
        return (this.numberOperations != 0) ? (this.points / this.numberOperations) : 0;
    }

    public void addIntention(Intention intention) {
        this.intentions.add(intention);
    }

    public void addPoints(int pointsToAdd) {
        this.setPoints(this.getPoints() + pointsToAdd);
    }

    public void subPoints(int pointsToSub) {
        this.setPoints(this.getPoints() - pointsToSub);
    }

    public void oneMoreOperation() {
        this.setNumberOperations(this.getNumberOperations() + 1);
    }

    public Set<Operation> operationsBetweenDates(long firstDate, long secondDate) {
        return this.operations.stream().filter(o -> o.getState().equals(OperationState.CRYPTOSENDED)
                && o.getDateTime() > firstDate
                && o.getDateTime() < secondDate).collect(Collectors.toSet());
    }
}