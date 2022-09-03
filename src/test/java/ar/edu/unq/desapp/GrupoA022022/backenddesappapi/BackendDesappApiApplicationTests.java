package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Model.User;
import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.annotations.Test;

import static org.testng.Assert.*;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;



@SpringBootTest
class BackendDesappApiApplicationTests {

	@Test
	void elNombreDeUnUsuarioEsCorrecto() {
		User user = new User();
		String name = "Graciela";
		user.setName(name);

		assertEquals(user.getName(), "Graciela");
	}

	@Test
	void elNombreDeUnUsuarioNoCumpleLasCondicionesLanzaUnaException(){
		User user = new User();
		String name = "";
		user.setName(name);


	}



}
