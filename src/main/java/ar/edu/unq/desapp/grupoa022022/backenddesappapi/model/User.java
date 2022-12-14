package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.Verify.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*
@Builder
*/
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "Lastname must be between 3 and 30 characters")
    private String lastname;

    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Email: must be a properly formatted email address")
    private String email;

    private String address;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*=])(?=\\S+$).{6,}$", message = "Password must contain at " +
            "least 1 lowercase, 1 uppercase, 1 special character, and at least 6 characters")
    private String password;

    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must be 22 characters")
    private String mercadoPagoCVU;

    @NotBlank
    @Size(min = 8, max = 8, message = "Wallet must be 8 characters")
    private String addressWalletActiveCrypto;

    private int points = 0;

    private int numberOperations = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Intention> intentions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userWhoAccepts", fetch = FetchType.EAGER)
    private Set<Operation> operations = new HashSet<>();

    public User(String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String addressWalletActiveCrypto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.addressWalletActiveCrypto = addressWalletActiveCrypto;
    }


    public void setName(String name) throws UserValidationException {
        if (verifyLong(name, 3, 30)) {
            this.name = name;
        } else {
            throw new UserValidationException("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }

    public void setLastname(String lastname) throws UserValidationException {
        if (verifyLong(lastname, 3, 30)) {
            this.lastname = lastname;
        } else {
            throw new UserValidationException("Campo Obligatorio. Debe tener entre 3 y 30 caracteres");
        }
    }

    public void setEmail(String email) throws UserValidationException {
        if (verifyEmail(email)) {
            this.email = email;
        } else {
            throw new UserValidationException("Campo Obligatoiro. Debe tener formato de email");
        }
    }

    public void setAddress(String address) throws UserValidationException {
        if (verifyLong(address, 10, 30)) {
            this.address = address;
        } else {
            throw new UserValidationException("Campo Obligatorio. Debe tener entre 10 y 30 caracteres");
        }
    }

    public void setPassword(String password) throws UserValidationException {
        if (verifyPassword(password)) {
            this.password = password;
        } else {
            throw new UserValidationException("Password: Must contain at least 1 lowercase, 1 uppercase, " +
                    "1 special character, and at least 6 characters");
        }
    }

    public void setMercadoPagoCVU(String mercadoPagoCVU) throws UserValidationException {
        if (verifyCVUMercadoPago(mercadoPagoCVU)) {
            this.mercadoPagoCVU = mercadoPagoCVU;
        } else {
            throw new UserValidationException("Campo Obligatorio. Debe contener 22 d??gitos");
        }
    }

    public String getAddressWalletActiveCrypto() {
        return addressWalletActiveCrypto;
    }

    public void setAddressWalletActiveCrypto(String adressWalletActiveCripto) throws UserValidationException {
        if (verifyaddressWalletActiveCrypto(adressWalletActiveCripto)) {
            this.addressWalletActiveCrypto = adressWalletActiveCripto;
        } else {
            throw new UserValidationException("Campo Obligatorio. Debe contener 8 d??gitos");
        }
    }

    public int getReputation() {
        return (this.numberOperations != 0) ? (this.points / this.numberOperations) : 0;
    }

    public void addIntention(Intention intention) {
        this.intentions.add(intention);
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
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
        Set<Operation> totalOperations = new HashSet<>();
        Set<Operation> operationsFromIntentions = this.intentions.stream().map(Intention::getOperation).filter(Objects::nonNull).collect(Collectors.toSet());
        totalOperations.addAll(operationsFromIntentions);
        totalOperations.addAll(this.operations);
        return totalOperations.stream().filter(o -> o.getState().equals(OperationState.CRYPTOSENT)
                && o.getDateTime() > firstDate
                && o.getDateTime() < secondDate).collect(Collectors.toSet());
    }

    public double volumeTraded(Set<Operation> operations) {
        return operations.stream().mapToDouble(o -> o.getIntention().amountPriceInPesos()).sum();
    }
}