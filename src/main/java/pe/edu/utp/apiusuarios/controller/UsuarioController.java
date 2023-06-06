package pe.edu.utp.apiusuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.apiusuarios.converter.UsuarioConverter;
import pe.edu.utp.apiusuarios.dto.UsuarioRequestDTO;
import pe.edu.utp.apiusuarios.dto.UsuarioResponseDTO;
import pe.edu.utp.apiusuarios.entity.Usuario;
import pe.edu.utp.apiusuarios.service.UsuarioService;
import pe.edu.utp.apiusuarios.util.WrapperResponse;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioConverter converter;
	
	@GetMapping()
	public ResponseEntity<List<UsuarioResponseDTO>> findAll(
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="offset",required=false, defaultValue = "0") int pageNumber,
			@RequestParam(value="limit",required=false, defaultValue = "5") int pageSize){
		
		Pageable pagina=PageRequest.of(pageNumber, pageSize);
		List<Usuario> registros;
		if(email==null) {
			registros=service.findAll(pagina);
		}else {
			registros=service.findByEmail(email, pagina);
		}
		List<UsuarioResponseDTO> registrosDTO=converter.fromEntity(registros);
		
		return new WrapperResponse(true,"success",registrosDTO).createResponse(HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<UsuarioResponseDTO> create (@RequestBody UsuarioRequestDTO usuario){
		Usuario registro = service.save(converter.registro(usuario));
		return new WrapperResponse(true,"success",converter.fromEntity(registro)).createResponse(HttpStatus.CREATED);
	}
}
