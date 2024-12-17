package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import init.dao.UsuariosDao;
import init.entities.Usuario;
import init.exception.DuplicateEmailException;
import init.model.UsuarioDto;
import init.service.UsuariosServiceImpl;
import init.utilidades.Mapeador;

@ExtendWith(MockitoExtension.class)
public class UsuariosServiceTests {
	
	@Mock
	UsuariosDao usuariosDao;
	
	@Mock
	Mapeador mapeador;
	
	@InjectMocks
	UsuariosServiceImpl usuariosServiceImpl;
	
	@Test
	@DisplayName("El usuario se puede dar de alta")
	public void altaUsuario_happyPath() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		when(usuariosDao.findByEmail(usuario.getEmail())).thenReturn(null);
		when(usuariosDao.findByUsername(usuario.getUsername())).thenReturn(null);
		when(mapeador.usuarioDtoNuevoUsuarioToEntity(usuarioDto)).thenReturn(usuario);
		when(usuariosDao.save(usuario)).thenReturn(usuario);
		when(mapeador.usuarioEntityToDto(usuario)).thenReturn(usuarioDto);
		
		//Act
		UsuarioDto usuarioPersistido = usuariosServiceImpl.altaUsuario(usuarioDto);
		
		//Assert
		assertAll("Verificación persistencia usuario",
				() -> assertEquals(usuario.getEmail(), usuarioPersistido.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuario.getUsername(), usuarioPersistido.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuario.getPassword(), usuarioPersistido.getPassword(),
																			"Las contraseñas no coinciden"));
	}
	
	@Test
	@DisplayName("El usuario NO se puede dar de alta - Emails duplicados")
	public void altaUsuario_emailsDuplicados() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		List<Usuario> usuarios = List.of();
		when(usuariosDao.findByEmail(usuario.getEmail())).thenReturn(usuarios);
		
		//Act & Assert
		assertThrows(DuplicateEmailException.class, () -> {
			usuariosServiceImpl.altaUsuario(usuarioDto);
		}, "Debería haberse lanzado una excepción DuplicateEmailException, pero no se ha lanzado");
		
	}

}
