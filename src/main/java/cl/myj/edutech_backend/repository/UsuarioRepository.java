package cl.myj.edutech_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.myj.edutech_backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
