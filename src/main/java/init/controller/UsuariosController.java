package init.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import init.model.UsuarioDto;
import init.service.UsuariosService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
public class UsuariosController {
	
	UsuariosService usuariosService;

	public UsuariosController(UsuariosService usuariosService) {
		this.usuariosService = usuariosService;
	}
	
	//Las excepciones que puedan lanzar los métodos de UsuariosService las maneja GlobalExceptionHandler

	@PostMapping(value="altaUsuario", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> altaUsuario(@Valid @RequestBody UsuarioDto usuario){
		usuariosService.altaUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente");
	}
		
	@DeleteMapping(value="bajaUsuario")
	public ResponseEntity<String> bajaUsuario(@RequestParam int idUsuario){
		usuariosService.bajaUsuario(idUsuario);
		return ResponseEntity.status(HttpStatus.OK).body("Usuario borrado correctamente");
	}
	
	@GetMapping(value="autenticacion")
	public ResponseEntity<String> autenticacion(@RequestParam String username, @RequestParam String password){
		boolean autenticacion = usuariosService.autenticacion(username, username);
		if(autenticacion) {
			return ResponseEntity.status(HttpStatus.OK).body("Autenticación llevada a cabo con éxito");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña no válidas");
	}
	
	@GetMapping(value="isAdmin")
	public ResponseEntity<String> isAdmin(@RequestParam int idUsuario){
		boolean isAdmin = usuariosService.isAdmin(idUsuario);
		if(isAdmin) {
			return ResponseEntity.status(HttpStatus.OK).body("El usuario tiene el rol de administrador");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario NO tiene el rol de administrador");
	}
	
}
