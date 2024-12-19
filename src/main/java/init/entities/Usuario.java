package init.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="users")
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;
	
    @Column(unique = true, nullable = false, length = 40)
    @Email(message = "El email debe tener un formato válido")
	private String email;
    
    @Column(unique = true, nullable = false, length = 20)
	private String username;
    
	@Column(nullable=false)
	private String password;
	
	@ManyToMany()
	@JoinTable(name="user_roles",
	joinColumns=@JoinColumn(name="idUsuario", referencedColumnName="idUsuario"),
	inverseJoinColumns=@JoinColumn(name="idRole", referencedColumnName="idRole"))
	private Set<Role> roles = new HashSet<>();
	
	public Usuario(int idUsuario, String email, String username, String password) {
		this.idUsuario = idUsuario;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Usuario() {
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
