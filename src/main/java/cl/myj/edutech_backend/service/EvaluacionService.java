package cl.myj.edutech_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.model.Evaluacion;
import cl.myj.edutech_backend.repository.CursoRepository;
import cl.myj.edutech_backend.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    @Autowired
    private CursoRepository cursoRepository;


    //crear
    public String crearEvaluacion(Evaluacion evaluacion) {
        if(evaluacion != null){
            evaluacionRepository.save(evaluacion);
            return "Evaluacion creada correctamente";
        }
        return "Error: Evaluacion no ha podido ser creado";
    }
    //mostrar
    public List<Evaluacion> listarEvaluacion() {
        return evaluacionRepository.findAll();
    }
    //asignar
    public String asignarEvaluacion(Integer idEvaluacion, Integer idCurso) {
        try {
            Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElseThrow(() -> new RuntimeException("Evaluacion no disponible"));
            Curso curso = cursoRepository.findById(idCurso).orElseThrow(() -> new RuntimeException("Curso no disponible"));
            evaluacion.setCurso(curso);
            evaluacionRepository.save(evaluacion);
            return "Evaluacion asignada correctamente";
        } catch(RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

}
