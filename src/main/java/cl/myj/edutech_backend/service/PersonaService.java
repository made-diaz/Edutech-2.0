package cl.myj.edutech_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.myj.edutech_backend.model.Persona;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.PersonaRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //crear
    public String crearPersona(Persona persona){
        if(persona != null){
            personaRepository.save(persona);
            return "Persona creado correctamente";
        }
        return "Error: La Persona no puedo ser creada";
    }

    //mostar
    public List<Persona> listarPersonas(){
        return personaRepository.findAll();
    }


    //Asignar usuario
    public String asignarUsuario(Integer idUsuario, Integer idPersona) {
            try {
                Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + idPersona));
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
                persona.setUsuario(usuario);
                personaRepository.save(persona);
                return "Usuario asignado!";
            } catch (RuntimeException e) {
                return "Error: " + e.getMessage();
            }
        }
}
