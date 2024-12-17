package init.service;

import init.model.UsuarioDto;

public interface UsuariosService {
	boolean altaUsuario(UsuarioDto usuario);
	boolean bajaUsuario(int idUsuario);
	boolean esAdmin(int idUsuario);
}
