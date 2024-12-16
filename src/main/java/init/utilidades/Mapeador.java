package init.utilidades;

import org.springframework.stereotype.Component;

import init.entities.Usuario;
import init.model.UsuarioDto;

@Component
public class Mapeador {
	public Usuario usuarioDtoToEntity(UsuarioDto usuario) {
		return new Usuario(usuario.getIdUsuario(),
						   usuario.getEmail(),
						   usuario.getUsername(),
						   usuario.getPassword());
	}
	
	public UsuarioDto usuarioEntityToDto(Usuario usuario) {
		return new UsuarioDto(usuario.getIdUsuario(),
						   	  usuario.getEmail(),
						   	  usuario.getUsername(),
						   	  usuario.getPassword());
	}
}
