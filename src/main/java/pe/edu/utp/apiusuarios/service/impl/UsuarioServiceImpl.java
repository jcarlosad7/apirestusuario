package pe.edu.utp.apiusuarios.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pe.edu.utp.apiusuarios.entity.Usuario;
import pe.edu.utp.apiusuarios.exception.GeneralServiceException;
import pe.edu.utp.apiusuarios.exception.NoDataFoundException;
import pe.edu.utp.apiusuarios.exception.ValidateServiceException;
import pe.edu.utp.apiusuarios.repository.UsuarioRepository;
import pe.edu.utp.apiusuarios.service.UsuarioService;
import pe.edu.utp.apiusuarios.validator.UsuarioValidator;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch(ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findByEmail(String email, Pageable page) {
		try {
			return repository.findByEmailContaining(email, page);
		} catch(ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(int id) {
		try {
			return repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el registro "+id));
		} catch(ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Usuario update(Usuario usuario) {
		try {
			UsuarioValidator.save(usuario);
			Usuario registroD = repository.findByEmail(usuario.getEmail()).orElseThrow(()-> new NoDataFoundException("No existe el usuario"));
			if(registroD !=null && registroD.getId()!= usuario.getId()) {
				throw new ValidateServiceException("Ya existe un registro con el email "+usuario.getEmail());
			}
			Usuario registro=repository.findById(usuario.getId()).orElseThrow(()-> new NoDataFoundException("No existe el registro con ese Id"));
			registro.setEmail(usuario.getEmail());
			//String passEncode=encoder.encode(usuario.getPassword());
			//registro.setPassword(passEncode);
			registro.setRol(usuario.getRol());
			repository.save(registro);
			return registro;
		} catch(ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

	@Override
	public Usuario save(Usuario usuario) {
		try {
			UsuarioValidator.save(usuario);
			Optional<Usuario> reg=repository.findByEmail(usuario.getEmail());
			if(reg.isPresent()) {
				throw new ValidateServiceException("Ya existe un registro con el email "+usuario.getEmail());
			}
			//String passEncode=encoder.encode(usuario.getPassword());
			//usuario.setPassword(passEncode);
			usuario.setActivo(true);
			Usuario registro =repository.save(usuario);
			return registro;
		} catch(ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			Usuario registro=repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe un registro con ese Id"));
			repository.delete(registro);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}		
	}

}
