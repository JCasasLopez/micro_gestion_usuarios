package init.service;

import init.model.UsuarioDto;

public interface UsuariosService {
	void altaUsuario(UsuarioDto usuario);
	void bajaUsuario(int idUsuario);
	boolean isAdmin(int idUsuario);
}
