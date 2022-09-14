package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@SpringBootTest
class BackendDesappApiApplicationTests {

    @Autowired
    private IUserRepo userrepo;

    private User prueUser = new User("Gaston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    @Test
    void elNombreDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String name = "Graciela";
        user.setName(name);
        System.out.println("en elNombreDeUnUsuarioEsCorrecto");

        assertEquals(user.getName(), "Graciela");
    }

    @Test
    void seRecuperaDeLaPersistenciaUnUsuarioNuevo() {
        User saved = userrepo.save(prueUser);
        int idSaved = saved.getId();
        Optional<User> finded = userrepo.findById(idSaved);

        assertEquals(finded.get().getId(), idSaved);
    }

    @Test
    void elApellidoDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String lastname = "Gonzalez";
        user.setLastname(lastname);

        assertEquals(user.getLastname(), "Gonzalez");
    }

    @Test
    void elApellidoDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void elEmailDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String email = "user@desp.com";
        user.setEmail(email);

        assertEquals(user.getEmail(), "user@desp.com");
    }

    @Test
    void elEmailDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void laDirecciónDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String adress = "Roque Saenz Peña 352";
        user.setAdress(adress);

        assertEquals(user.getAdress(), "Roque Saenz Peña 352");
    }

    @Test
    void laDireccionDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String adress = "Roque 352";
            user.setAdress(adress);
        });
    }

    @Test
    void laContraseñaDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String password = "Desarrollo1!";
        user.setPassword(password);

        assertEquals(user.getPassword(), "Desarrollo1!");
    }

    @Test
    void laContraseñaDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String password = "Desa!";
            user.setPassword(password);
        });
    }

    @Test
    void elCVDeMercadoLibreDeUnUsuarioEsCorrecto() throws ExceptionsUser {
        User user = new User();
        String CVUMercadoPago = "1234567890123456789012";
        user.setCVUMercadoPago(CVUMercadoPago);

        assertEquals(user.getCVUMercadoPago(), "1234567890123456789012");
    }

    @Test
    void elCVDeMercadoLibreDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String CVUMercadoPago = "123456789012345678901";
            user.setPassword(CVUMercadoPago);
        });
    }

    @Test
    void laDirecciónBilleteraDeCriptoActivosDeUnUsuarioEsCorrecta() throws ExceptionsUser {
        User user = new User();
        String adressWalletActiveCripto = "12345678";
        user.setAdressWalletActiveCripto(adressWalletActiveCripto);

        assertEquals(user.getAdressWalletActiveCripto(), "12345678");
    }

    @Test
    void laDirecciónBilleteraDeCriptoActivosNoCumpleLasCondicionesLanzaUnaException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String adressWalletActiveCripto = "123456789";
            user.setAdressWalletActiveCripto(adressWalletActiveCripto);
        });
    }
}
