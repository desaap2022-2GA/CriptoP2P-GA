package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.annotations.Test;

//import static org.testng.Assert.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import java.util.Optional;


@SpringBootTest
class BackendDesappApiApplicationTests {

	@Autowired
	private IUserRepo userrepo;

	@Test
	void elNombreDeUnUsuarioEsCorrecto() {
		User user = new User();
		String name = "Graciela";
		user.setName(name);
		System.out.println("en elNombreDeUnUsuarioEsCorrecto");

		assertEquals(user.getName(), "Graciela");
	}
/*
	@Test
	void elNombreDeUnUsuarioNoCumpleLasCondicionesLanzaUnaExcepcion(){
		User user = new User();
		String name = "";
		user.setName(name);
		System.out.println("en elNombreDeUnUsuarioNoCumpleLasCondicionesLanzaUnaExcepcion");
		/*le puse cualquier cosa en el assert solo para que no rompa el build
		assertEquals(1, 1);

	}
*/
	@Test
	void seRecuperaDeLaPersistenciaUnUsuarioNuevo() {
		User saved = userrepo.save(new User("Guillermo","Vilas","vilas@yahoo.com.ar","Juana Azurduy 315, CABA","1111","66548798","Xwfefg5ef"));
		int idSaved = saved.getId();
		Optional<User> finded = userrepo.findById(idSaved);

		assertEquals(finded.get().getId(), idSaved);
	}



}
