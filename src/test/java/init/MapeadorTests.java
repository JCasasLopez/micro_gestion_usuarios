package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import init.entities.Usuario;
import init.model.UsuarioDto;
import init.utilidades.Mapeador;

//Todas estas anotaciones evitan tener que levantar el contexto entero con @SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(Mapeador.class)
public class MapeadorTests {

	@Autowired
	private Mapeador mapeador;

	@Test
	@DisplayName("Mapea correctamente de UsuarioDto a Usuario")
	public void mapeaCorrectamenteAUsuario() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto(0, "a@gmail.com", "Yorch", "1234");
		
		//Act
		Usuario usuarioMapeado = mapeador.usuarioDtoToEntity(usuarioDto);
		
		//Assert
		assertAll("Verificación mapeo UsuarioDto a Usuario",
				() -> assertEquals(usuarioDto.getIdUsuario(), usuarioMapeado.getIdUsuario(),
																			"Los idUsuario no coinciden"),
				() -> assertEquals(usuarioDto.getEmail(), usuarioMapeado.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuarioDto.getUsername(), usuarioMapeado.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuarioDto.getPassword(), usuarioMapeado.getPassword(),
																			"Las contraseñas no coinciden"));
	}
	
	@Test
	@DisplayName("Mapea correctamente de Usuario a UsuarioDto")
	public void mapeaCorrectamenteAUsuarioDto() {
		//Arrange
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		
		//Act
		UsuarioDto usuarioMapeado = mapeador.usuarioEntityToDto(usuario);
		
		//Assert
		assertAll("Verificación mapeo UsuarioDto a Usuario",
				() -> assertEquals(usuarioDto.getIdUsuario(), usuarioMapeado.getIdUsuario(),
																			"Los idUsuario no coinciden"),
				() -> assertEquals(usuarioDto.getEmail(), usuarioMapeado.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuarioDto.getUsername(), usuarioMapeado.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuarioDto.getPassword(), usuarioMapeado.getPassword(),
																			"Las contraseñas no coinciden"));
	}
}
