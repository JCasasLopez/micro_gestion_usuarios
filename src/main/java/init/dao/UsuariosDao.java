package init.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import init.entities.Usuario;

public interface UsuariosDao extends JpaRepository<Usuario, Integer> {
	Usuario findByEmail(String email);
	Usuario findByUsername(String username);
}
