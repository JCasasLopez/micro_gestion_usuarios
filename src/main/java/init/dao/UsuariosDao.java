package init.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import init.entities.Usuario;

public interface UsuariosDao extends JpaRepository<Usuario, Integer> {
	List<Usuario> findByEmail(String email);
	List<Usuario> findByUsername(String username);
}
