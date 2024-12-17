package init.utilidades;

import org.springframework.stereotype.Component;

import init.entities.Usuario;
import init.model.UsuarioDto;

@Component
public class Mapeador {
	//¿Vamos a necesitar este método realmente?
	public Usuario usuarioDtoToEntity(UsuarioDto usuario) {
		return new Usuario(usuario.getIdUsuario(),
						   usuario.getEmail(),
						   usuario.getUsername(),
						   usuario.getPassword());
	}
	
	public Usuario usuarioDtoNuevoUsuarioToEntity(UsuarioDto usuario) {
		return new Usuario(0,
						   usuario.getEmail(),
						   usuario.getUsername(),
						   usuario.getPassword());
	}
	
	public UsuarioDto usuarioEntityToDto(Usuario usuario) {
		return new UsuarioDto(usuario.getIdUsuario(),
						   	  usuario.getEmail(),
						   	  usuario.getUsername(),
						   	  usuario.getPassword(),
						   	  usuario.getRoles());
	}
	
}
