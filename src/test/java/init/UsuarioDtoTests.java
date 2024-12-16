package init;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import init.model.UsuarioDto;

public class UsuarioDtoTests {
	
	@Test
	@DisplayName("El rol de usuario se asigna automáticamente de forma correcta")
	public void rolUsuarioAsignado() {
		//Arrange
		
		//Act
		UsuarioDto usuario = new UsuarioDto("a@gmail.com", "Yorch", "1234");

		//Assign
		assertTrue(usuario.getRoles().size() == 1);
	}
}
