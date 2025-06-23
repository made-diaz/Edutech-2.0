package cl.myj.edutech_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.myj.edutech_backend.model.Contenido;
import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.repository.ContenidoRepository;
import cl.myj.edutech_backend.repository.CursoRepository;

@Service
public class ContenidoService {
    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //CREAR CONTENIDO
    public String crearContenido(Contenido contenido) {
        if(contenido != null){
            contenido.setEstado(true);
            contenidoRepository.save(contenido);
            return "Contenido creado correctamente";
        }
        return "Error: Contenido no ha podido ser creado";
        
    }

    //LISTAR CONTENIDO
    public List<Contenido> listarContenido(){
        return contenidoRepository.findAll();    
    }

    //ASIGNAR A CURSO

    public String asignarContenido(Integer idContenido, Integer idCurso) {
        try {
            Contenido contenido = contenidoRepository.findById(idContenido).orElseThrow(() -> new RuntimeException("Contenido no disponible"));
            Curso curso = cursoRepository.findById(idCurso).orElseThrow(() -> new RuntimeException("Curso no disponible"));
            contenido.setCurso(curso);
            contenidoRepository.save(contenido);
            return "Contenido asignado correctamente";
        } catch(RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    } 


}
