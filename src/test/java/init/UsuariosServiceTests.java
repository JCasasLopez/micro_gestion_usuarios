package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import init.dao.UsuariosDao;
import init.entities.Role;
import init.entities.Usuario;
import init.exception.DuplicateEmailException;
import init.exception.DuplicateUsernameException;
import init.exception.NoSuchUserException;
import init.exception.UsuarioDatabaseException;
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
	@DisplayName("El usuario se dio de alta correctamente")
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
		var inOrder = inOrder(usuariosDao, usuariosDao, mapeador, usuariosDao, mapeador);
		inOrder.verify(usuariosDao).findByEmail(usuario.getEmail());
		inOrder.verify(usuariosDao).findByUsername(usuario.getUsername());
		inOrder.verify(mapeador).usuarioDtoNuevoUsuarioToEntity(usuarioDto);
		inOrder.verify(usuariosDao).save(usuario);
		inOrder.verify(mapeador).usuarioEntityToDto(usuario);
		
		assertAll("Verificación persistencia usuario",
				() -> assertEquals(usuario.getEmail(), usuarioPersistido.getEmail(),
																			"Los emails no coinciden"),
				() -> assertEquals(usuario.getUsername(), usuarioPersistido.getUsername(),
																			"Los usernames no coinciden"),
				() -> assertEquals(usuario.getPassword(), usuarioPersistido.getPassword(),
																			"Las contraseñas no coinciden"));
	}
	
	@Test
	@DisplayName("Emails duplicados - El usuario NO se puede dar de alta")
	public void altaUsuario_emailsDuplicados() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		List<Usuario> usuarios = List.of();
		when(usuariosDao.findByEmail(usuario.getEmail())).thenReturn(usuarios);
		
		//Act & Assert
		assertThrows(DuplicateEmailException.class, () -> {
			usuariosServiceImpl.altaUsuario(usuarioDto);
		}, "No se ha lanzado una excepción DuplicateEmailException como se esperaba");
		
	}
	
	@Test
	@DisplayName("Usernames duplicados - El usuario NO se puede dar de alta")
	public void altaUsuario_usernamesDuplicados() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		List<Usuario> usuarios = List.of();
		when(usuariosDao.findByEmail(usuario.getEmail())).thenReturn(null);
		when(usuariosDao.findByUsername(usuario.getUsername())).thenReturn(usuarios);
		
		//Act & Assert
		assertThrows(DuplicateUsernameException.class, () -> {
			usuariosServiceImpl.altaUsuario(usuarioDto);
		}, "No se ha lanzado una excepción DuplicateUsernameException como se esperaba");
		
	}
	
	@Test
	@DisplayName("Error base de datos - El usuario NO se puede dar de alta")
	public void altaUsuario_errorBaseDatos() {
		//Arrange
		UsuarioDto usuarioDto = new UsuarioDto("a@gmail.com", "Yorch", "1234");
		Usuario usuario = new Usuario(0, "a@gmail.com", "Yorch", "1234");
		when(usuariosDao.findByEmail(usuario.getEmail())).thenReturn(null);
		when(usuariosDao.findByUsername(usuario.getUsername())).thenReturn(null);
		when(mapeador.usuarioDtoNuevoUsuarioToEntity(usuarioDto)).thenReturn(usuario);
		when(usuariosDao.save(usuario)).thenThrow(new DataIntegrityViolationException("Error simulado"));
		
		//Act & Assert
		assertThrows(UsuarioDatabaseException.class, () -> {usuariosServiceImpl.altaUsuario(usuarioDto);}, 
				"No se ha lanzado una excepción UsuarioDatabaseException como se esperaba");
	}
	
	@Test
	@DisplayName("El usuario se borró correctamente")
	public void bajaUsuario_happyPath() {
		//Arrange
		int idUsuario = 0;
		Usuario usuario = new Usuario();
	    Optional<Usuario> optionalUsuario = Optional.of(usuario);
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		doNothing().when(usuariosDao).deleteById(idUsuario);
		
		//Act
		usuariosServiceImpl.bajaUsuario(idUsuario);
		
		//Assert
		verify(usuariosDao, times(1)).deleteById(idUsuario);
	}
	
	@Test
	@DisplayName("No existe ese usuario - El usuario NO se borró")
	public void bajaUsuario_NoSuchUserException() {
		//Arrange
		int idUsuario = 0;
		Optional<Usuario> optionalUsuario = Optional.empty();
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		
		//Act & Assert
		assertThrows(NoSuchUserException.class, () -> {usuariosServiceImpl.bajaUsuario(idUsuario);}, 
				"No se ha lanzado una excepción NoSuchUserException como se esperaba");
	}
	
	@Test
	@DisplayName("Error en la base de datos - El usuario NO se borró")
	public void bajaUsuario_errorBaseDatos() {
		//Arrange
		int idUsuario = 0;
		Usuario usuario = new Usuario();
	    Optional<Usuario> optionalUsuario = Optional.of(usuario);
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		doThrow(new DataIntegrityViolationException("Error simulado")).when(usuariosDao).deleteById(idUsuario);		
		
		//Act & Assert
		assertThrows(UsuarioDatabaseException.class, () -> {usuariosServiceImpl.bajaUsuario(idUsuario);}, 
						"No se ha lanzado una excepción UsuarioDatabaseException como se esperaba");
	}
	
	@Test
	@DisplayName("El usuario es admin")
	public void isAdmin_usuarioEsAdmin() {
		//Arrange
		int idUsuario = 0;
		Set<Role> roles = new HashSet<>(Set.of(new Role(0, "ROLE_USER"), new Role(1, "ROLE_ADMIN")));
		Usuario usuario = new Usuario(idUsuario, "a@gmail.com", "Yorch", "1234");
		usuario.setRoles(roles);
	    Optional<Usuario> optionalUsuario = Optional.of(usuario);
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		
		//Act
		boolean isAdmin = usuariosServiceImpl.isAdmin(idUsuario);
		
		//Assert
		assertTrue(isAdmin, "Se esperaba que el resultado fuera true");
	}
	
	@Test
	@DisplayName("El usuario NO es admin")
	public void isAdmin_usuarioNoEsAdmin() {
		//Arrange
		int idUsuario = 0;
		Set<Role> roles = new HashSet<>(Set.of(new Role(0, "ROLE_USER"), new Role(1, "ROLE_GUEST")));
		Usuario usuario = new Usuario(idUsuario, "a@gmail.com", "Yorch", "1234");
		usuario.setRoles(roles);
	    Optional<Usuario> optionalUsuario = Optional.of(usuario);
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		
		//Act
		boolean isAdmin = usuariosServiceImpl.isAdmin(idUsuario);
		
		//Assert
		assertFalse(isAdmin, "Se esperaba que el resultado fuera false");
	}
	
	@Test
	@DisplayName("El usuario NO tiene ningún rol")
	public void isAdmin_usuarioNoTieneRoles() {
		//Arrange
		int idUsuario = 0;
		Usuario usuario = new Usuario(idUsuario, "a@gmail.com", "Yorch", "1234");
	    Optional<Usuario> optionalUsuario = Optional.of(usuario);
		when(usuariosDao.findById(idUsuario)).thenReturn(optionalUsuario);
		
		//Act
		boolean isAdmin = usuariosServiceImpl.isAdmin(idUsuario);
		
		//Assert
		assertFalse(isAdmin, "Se esperaba que el resultado fuera false");
	}
	
	@Test
	@DisplayName("NoSuchUserException - No se encontró al usuario")
	public void isAdmin_noSeEncontroAlUsuario() {
		//Arrange
		int idUsuario = 0;
		when(usuariosDao.findById(idUsuario)).thenReturn(Optional.empty());
		
		//Act & Assert
		assertThrows(NoSuchUserException.class, () -> {usuariosServiceImpl.isAdmin(idUsuario);}, 
				"No se ha lanzado una excepción NoSuchUserException como se esperaba");
	}

	@Test
	@DisplayName("UsuarioDatabaseException - Error en la base de datos")
	public void isAdmin_errorBaseDatos() {
		//Arrange
		int idUsuario = 0;
		when(usuariosDao.findById(idUsuario)).thenThrow(new UsuarioDatabaseException("Error simulado"));
		
		//Act & Assert
		assertThrows(UsuarioDatabaseException.class, () -> {usuariosServiceImpl.isAdmin(idUsuario);}, 
				"No se ha lanzado una excepción UsuarioDatabaseException como se esperaba");
	}
	
}
	