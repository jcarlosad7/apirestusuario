package pe.edu.utp.apiusuarios.converter;

import org.springframework.stereotype.Component;

import pe.edu.utp.apiusuarios.dto.UsuarioRequestDTO;
import pe.edu.utp.apiusuarios.dto.UsuarioResponseDTO;
import pe.edu.utp.apiusuarios.entity.Rol;
import pe.edu.utp.apiusuarios.entity.Usuario;

@Component
public class UsuarioConverter extends AbstractConverter<Usuario, UsuarioResponseDTO> {

	@Override
	public UsuarioResponseDTO fromEntity(Usuario entity) {
		if (entity==null)return null;
		return UsuarioResponseDTO.builder()
				.id(entity.getId())
				.email(entity.getEmail())
				.activo(entity.isActivo())
				.rol(entity.getRol().name())
				.build();
	}

	@Override
	public Usuario fromDTO(UsuarioResponseDTO dto) {
		if(dto==null)return null;
		Rol rol=Rol.valueOf(dto.getRol().toUpperCase());
		return Usuario.builder()
				.id(dto.getId())
				.email(dto.getEmail())
				.activo(dto.isActivo())
				.rol(rol)
				.build();
	}
	
	public Usuario registro(UsuarioRequestDTO dto) {
		if (dto==null) return null;
		Rol rol=Rol.valueOf(dto.getRol().toUpperCase());
		return Usuario.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.rol(rol)
				.build();
	}
}
