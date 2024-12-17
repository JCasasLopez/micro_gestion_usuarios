package init.service;

import init.model.UsuarioDto;

public interface UsuariosService {
	void altaUsuario(UsuarioDto usuario);
	boolean bajaUsuario(int idUsuario);
	boolean esAdmin(int idUsuario);
}
