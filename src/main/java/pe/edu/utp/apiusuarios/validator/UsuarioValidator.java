package pe.edu.utp.apiusuarios.validator;

import pe.edu.utp.apiusuarios.entity.Usuario;
import pe.edu.utp.apiusuarios.exception.ValidateServiceException;

public class UsuarioValidator {
	public static void save(Usuario usuario) {
		if(usuario.getEmail()==null) {
			throw new ValidateServiceException("El email es requerido");
		}
		if(usuario.getEmail().length()<=0) {
			throw new ValidateServiceException("El email es requerido");
		}
		if(usuario.getPassword()==null) {
			throw new ValidateServiceException("El password es requerido");
		}
		if(usuario.getPassword().length()<=0) {
			throw new ValidateServiceException("El password es requerido");
		}
		if(usuario.getRol()==null) {
			throw new ValidateServiceException("El rol es requerido");
		}
	}
}
