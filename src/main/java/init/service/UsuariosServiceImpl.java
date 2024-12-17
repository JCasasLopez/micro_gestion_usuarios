package init.service;

import init.dao.UsuariosDao;
import init.model.UsuarioDto;

public class UsuariosServiceImpl implements UsuariosService {
	
	UsuariosDao usuariosDao;
	
	public UsuariosServiceImpl(UsuariosDao usuariosDao) {
		this.usuariosDao = usuariosDao;
	}

	@Override
	public boolean altaUsuario(UsuarioDto usuario) {
		
		return false;
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
