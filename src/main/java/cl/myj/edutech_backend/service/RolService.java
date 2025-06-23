package cl.myj.edutech_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.myj.edutech_backend.model.Rol;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.RolRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //crear rol
    public String crearRol(Rol rol){
        if(rol != null){
            rolRepository.save(rol);
            return "Rol creado correctamente";
        }
        return "Error: Rol no creado";
    }

    //listar
    public List<Rol> listarRoles(){
        return rolRepository.findAll();
    }

    //asiociar rol a usuario
    public String asociarRolAUsuario(Integer usuarioId, Integer rolId){
        if(usuarioId == null || rolId == null){
            return "Error: Los IDs no pueden ser nulos";
        }
        try{
            Usuario usuario = usuarioRepository.findById(usuarioId).get();
            Rol rol = rolRepository.findById(rolId).get();

            usuario.setRol(rol);
            usuarioRepository.save(usuario);
            return "Rol Asignado correctamente";
        } catch(Exception e){
            return "Error: " + e.getMessage();
        }
    }
}
