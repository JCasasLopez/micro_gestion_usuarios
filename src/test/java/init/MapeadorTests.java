package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

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
	@DisplayName("Mapea correctamente de UsuarioDto (normal) a Usuario")
	public void mapeaCorrectamenteAUsuario() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto(0, "a@gmail.com", "Yorch", "1234", new HashSet<>());
		
		//Act
		Usuario usuarioMapeado = mapeador.usuarioDtoNormalToEntity(usuarioDto);
		
		//Assert
		assertAll("Verificación mapeo UsuarioDto a Usuario - Versión normal",
				() -> assertEquals(usuarioDto.getIdUsuario(), usuarioMapeado.getIdUsuario(),
																			"Los idUsuario no coinciden"),
				() -> assertEquals(usuarioDto.getEmail(), usuarioMapeado.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuarioDto.getUsername(), usuarioMapeado.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuarioDto.getPassword(), usuarioMapeado.getPassword(),
																			"Las contraseñas no coinciden"),
				() -> assertEquals(usuarioDto.getRoles(), usuarioMapeado.getRoles(), 
																			"Los sets de roles no coinciden"));
	}
	
	@Test
	@DisplayName("Mapea correctamente de UsuarioDto (nuevo usuario) a Usuario")
	public void mapeaCorrectamenteAUsuarioNuevoUsuario() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		
		//Act
		Usuario usuarioMapeado = mapeador.usuarioDtoNuevoUsuarioToEntity(usuarioDto);
		
		//Assert
		assertAll("Verificación mapeo UsuarioDto a Usuario - Nuevo Usuario",
				() -> assertEquals(usuarioDto.getIdUsuario(), 0, "idUsuario no es 0"),
				() -> assertEquals(usuarioDto.getEmail(), usuarioMapeado.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuarioDto.getUsername(), usuarioMapeado.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuarioDto.getPassword(), usuarioMapeado.getPassword(),
																			"Las contraseñas no coinciden"),
				() -> assertEquals(usuarioDto.getRoles().size(), 1, "No se ha asignado el rol usuario"));
	}
	
	//Solo mapea a la versión "completa" de UsuarioDto
	//No tiene sentido mapear a la otra, ya que ese constructor solo sea usa para crear
	//nuevos usuarios, es decir, solo va a funcionar en la otra dirección (de Entity a Dto)
	@Test
	@DisplayName("Mapea correctamente de Usuario a UsuarioDto (normal)")
	public void mapeaCorrectamenteAUsuarioDto() {
		//Arrange
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		
		//Act
		UsuarioDto usuarioMapeado = mapeador.usuarioEntityToDto(usuario);
		
		//Assert
		assertAll("Verificación mapeo Usuario a UsuarioDto",
				() -> assertEquals(usuario.getIdUsuario(), usuarioMapeado.getIdUsuario(),
																			"Los idUsuario no coinciden"),
				() -> assertEquals(usuario.getEmail(), usuarioMapeado.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuario.getUsername(), usuarioMapeado.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuario.getPassword(), usuarioMapeado.getPassword(),
																			"Las contraseñas no coinciden"));
	}
}
