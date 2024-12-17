package init.service;

import java.util.Optional;
import java.util.Set;

import init.dao.UsuariosDao;
import init.entities.Role;
import init.entities.Usuario;
import init.exception.DuplicateEmailException;
import init.exception.DuplicateUsernameException;
import init.exception.NoSuchUserException;
import init.exception.UsuarioDatabaseException;
import init.model.UsuarioDto;
import init.utilidades.Mapeador;

public class UsuariosServiceImpl implements UsuariosService {
	
	UsuariosDao usuariosDao;
	Mapeador mapeador;
	private static final String ROLE_ADMIN = "USER_ADMIN";
	
	public UsuariosServiceImpl(UsuariosDao usuariosDao, Mapeador mapeador) {
		this.usuariosDao = usuariosDao;
		this.mapeador = mapeador;
	}

	@Override
	public void altaUsuario(UsuarioDto usuario) {
		try {
			
			if(usuariosDao.findByEmail(usuario.getEmail()) != null) {
				throw new DuplicateEmailException("Ya existe un usuario con ese email");
			}
			
			if(usuariosDao.findByUsername(usuario.getUsername()) != null) {
				throw new DuplicateUsernameException("Ya existe un usuario con ese username");
			}
			
			usuariosDao.save(mapeador.usuarioDtoNuevoUsuarioToEntity(usuario));
			
		} catch (Exception ex) {
			throw new UsuarioDatabaseException("Error al intentar persistir usuario en la base de datos");
		}
	}

	@Override
	public void bajaUsuario(int idUsuario) {
		try {
			
			if(usuariosDao.findById(idUsuario).isEmpty()) {
				throw new NoSuchUserException("No existe ningún usuario con ese idUsuario");
			}
			
			usuariosDao.deleteById(idUsuario);
			
		} catch (Exception ex) {
			throw new UsuarioDatabaseException("Error al intentar borrar usuario en la base de datos");
		}
	
	}

	@Override
	public boolean isAdmin(int idUsuario) {
		try {
			
			boolean usuarioEsAdmin = false;
			Optional<Usuario> usuarioBuscado = usuariosDao.findById(idUsuario);
			
			if(usuarioBuscado.isEmpty()) {
				throw new NoSuchUserException("No existe ningún usuario con ese idUsuario");
			}
			
			Set<Role> roles = usuarioBuscado.get().getRoles();
			for(Role r:roles) {
				if(r.getNombre().equals(ROLE_ADMIN)) {
					usuarioEsAdmin = true;
				}
			}
			return usuarioEsAdmin;
			
		} catch (Exception ex) {
			throw new UsuarioDatabaseException("Error al intentar consultar con la base de datos");
		}

	}

}
