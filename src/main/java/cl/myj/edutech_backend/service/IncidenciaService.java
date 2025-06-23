package cl.myj.edutech_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.myj.edutech_backend.model.Incidencia;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.IncidenciaRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;

@Service
public class IncidenciaService {
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //crear y asignar
    public String crearIncidencia(Incidencia incidencia, Integer idUsuario) {
        if(incidencia != null){
            try{
                Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
                incidencia.setUsuario(usuario);
                incidenciaRepository.save(incidencia);
                return "Incidencia creada correctamente";
            } catch(RuntimeException e) {
                return "Error: " + e.getMessage();
            }
        }
        return "Error: incidencia nula"; 
        
    }
    //mostrar
    public List<Incidencia> listarIncidencia() {
        return incidenciaRepository.findAll();
    }



}
