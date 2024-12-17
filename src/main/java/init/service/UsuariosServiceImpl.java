package init.service;

import init.dao.UsuariosDao;
import init.exception.EmailNotAvailableException;
import init.exception.UsernameNotAvailable;
import init.model.UsuarioDto;

public class UsuariosServiceImpl implements UsuariosService {
	
	UsuariosDao usuariosDao;
	
	public UsuariosServiceImpl(UsuariosDao usuariosDao) {
		this.usuariosDao = usuariosDao;
	}

	@Override
	public boolean altaUsuario(UsuarioDto usuario) {
		if(usuariosDao.findByEmail(usuario.getEmail()) != null) {
			throw new EmailNotAvailableException("Ya existe un usuario con ese email");
		}
		
		if(usuariosDao.findByUsername(usuario.getUsername()) != null) {
			throw new UsernameNotAvailable("Ya existe un usuario con ese username");
		}
	}

	@Override
	public boolean bajaUsuario(int idUsuario) {
		
		return false;
	}

	@Override
	public boolean esAdmin(int idUsuario) {
	
		return false;
	}

}
