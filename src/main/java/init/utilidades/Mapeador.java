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
	
	//No tiene sentido mapear de Usuario a la versión "nuevo usuario" de UsuarioDto, ya que ese 
	//constructor solo sea usa para crear nuevos usuarios, es decir, solo va a funcionar en la 
	//otra dirección (de Entity a Dto).
	
}
