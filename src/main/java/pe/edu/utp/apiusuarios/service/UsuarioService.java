package pe.edu.utp.apiusuarios.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import pe.edu.utp.apiusuarios.entity.Usuario;


public interface UsuarioService {
	public List<Usuario> findAll(Pageable page);
	public List<Usuario> findByEmail(String email, Pageable page);
	public Usuario findById(int id);
	public Usuario update(Usuario usuario);
	public Usuario save(Usuario usuario);
	public void delete(int id);
}
