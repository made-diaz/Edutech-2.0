package cl.myj.edutech_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.myj.edutech_backend.model.Evaluacion;
import cl.myj.edutech_backend.service.EvaluacionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/evaluacion")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    //crear
    @PostMapping
    public String crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.crearEvaluacion(evaluacion);
    }
    //MOSTRAR
    @GetMapping
    public List<Evaluacion> listarEvaluacion() {
        return evaluacionService.listarEvaluacion();
    }
    //ASIGNAR
    @PostMapping("/asignarCurso/{idEvaluacion}/{idCurso}")
    public String asignarContenido(@PathVariable Integer idEvaluacion, @PathVariable Integer idCurso) {
        return evaluacionService.asignarEvaluacion(idEvaluacion, idCurso);
    }

}
