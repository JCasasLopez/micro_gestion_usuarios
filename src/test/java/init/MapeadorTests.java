package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import init.entities.Role;
import init.entities.Usuario;
import init.model.UsuarioDto;
import init.utilidades.Mapeador;

//Todas estas anotaciones evitan tener que levantar el contexto entero con @SpringBootTest

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(Mapeador.class)
public class MapeadorTests {
	
	//Solo testeo los casos "happy-path" ya que los datos serán validados en capas superiores.

	@Autowired
	private Mapeador mapeador;

	@Test
	@DisplayName("Mapea correctamente de UsuarioDto (normal) a Usuario")
	public void mapeaCorrectamenteAUsuario() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto(0, "a@gmail.com", "Yorch", "1234", new HashSet<>());
		
		//Act
		Usuario usuarioMapeado = mapeador.usuarioDtoToEntity(usuarioDto);
		
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
				() -> assertEquals(usuarioDto.getRoles().size(), 1, "No se ha asignado el rol usuario"),
				() -> assertEquals("ROLE_USER", usuarioDto.getRoles().iterator().next().getNombre(), 
					    												"El rol asignado no es ROLE_USER"));
	}
		
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
	
	@Test
	@DisplayName("Mapea correctamente usuario con múltiples roles")
	public void mapeaCorrectamenteUsuarioConMultiplesRoles() {
	    //Arrange
	    Set<Role> roles = new HashSet<>();
	    roles.add(new Role(1, "ROLE_USER"));
	    roles.add(new Role(2, "ROLE_ADMIN"));
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
	    usuario.setRoles(roles);

	    //Act
	    UsuarioDto usuarioMapeado = mapeador.usuarioEntityToDto(usuario);

	    //Assert
	    assertEquals(2, usuarioMapeado.getRoles().size(), "No se mapearon todos los roles");
	    assertTrue(usuarioMapeado.getRoles().containsAll(roles), "Los roles no coinciden");
	}
}
