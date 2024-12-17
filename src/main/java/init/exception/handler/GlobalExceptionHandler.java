package init.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import init.exception.DuplicateEmailException;
import init.exception.DuplicateUsernameException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

}

