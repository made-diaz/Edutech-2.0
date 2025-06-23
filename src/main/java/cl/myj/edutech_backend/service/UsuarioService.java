package cl.myj.edutech_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.myj.edutech_backend.model.Persona;
import cl.myj.edutech_backend.model.Rol;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.PersonaRepository;
import cl.myj.edutech_backend.repository.RolRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PersonaRepository personaRepository;


    //ADMIN
        //CREAR
        public String crearUsuario(Usuario usuario) {
            if(usuario != null) {
                usuario.setEstado(true);
                usuarioRepository.save(usuario);
                return "Usuario creado correctamente!";
            }
            return "Error: usuario nulo";
        }
        //ACTUALIZAR
        public String actualizarUsuario(Usuario usuarioActualizado) {
            if(usuarioActualizado != null && usuarioActualizado.getId() != null) {
                Usuario usuarioExistente = usuarioRepository.findById(usuarioActualizado.getId()).orElse(null);
                if(usuarioExistente != null){
                    if(usuarioActualizado.getNombre() != null){
                        usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    }
                    if(usuarioActualizado.getClave() != null){
                        usuarioExistente.setClave(usuarioActualizado.getClave());
                    }
                    if(usuarioActualizado.getCorreo() != null){
                        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());;
                    }
                    usuarioRepository.save(usuarioExistente);
                    return "Usuario actualizado correctamente";
                }
                return "Error: usuario no encontrado";
            }
            return "Error: Datos dce usuario invalido";
        }
        //DESACTIVAR
        public String desactivarUsuario(Integer id) {
            try {
                Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
                usuario.setEstado(false);
                usuarioRepository.save(usuario);
                return "Usuario desactivado!";
            } catch (RuntimeException e) {
                return "Error: " + e.getMessage();
            }
            

        }
        //ELIMINAR
        public String eliminarUsuario(Integer id) {
            try{
                Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
                usuarioRepository.delete(usuario);
                return "Usuario eliminado!";
            } catch (RuntimeException e) {
                return "Error: " + e.getMessage();
            }
            

        }
        //ASIGNAR ROL
        public String asignarRol(Integer idRol, Integer idUsuario) {
            try {
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario));
                Rol rol = rolRepository.findById(idRol).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + idRol));
                usuario.setRol(rol);
                return "Rol asignado!";        
            } catch (RuntimeException e){
                return "Error: " + e.getMessage();
            }
        }

        
    

    //MOSTRAR USUARIOS
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    //MOSTRAR ROLES
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
    //MOSTRAR PERSONAS    
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }



}
