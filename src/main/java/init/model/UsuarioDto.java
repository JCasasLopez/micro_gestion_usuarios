package init.model;

import java.util.HashSet;
import java.util.Set;

import init.entities.Role;

public class UsuarioDto {
	private int idUsuario;
	private String email;
	private String username;
	private String password;
	private Set<Role> roles;
	
	public UsuarioDto(int idUsuario, String email, String username, String password) {
		this.idUsuario = idUsuario;
		this.email = email;
		this.username = username;
		this.password = password;
		//Automáticamente le asigna el rol de usuario. Para convertirle en ADMIN, hay que añadir este
		//rol manualmente en la base de datos
		this.roles = new HashSet<Role>();
		roles.add(new Role(0, "ROLE_USER"));
	}

	public UsuarioDto() {
		super();
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
