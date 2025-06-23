package cl.myj.edutech_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    //Crear
    @PostMapping("/profesor/crearCurso")
    public String crearCurso(@RequestBody Curso curso){
        return cursoService.crearCurso(curso);
    }

    //Listar
    @GetMapping
    public List<Curso> listaCursos(){
        return cursoService.listarCurso();
    }

    //Asignar profesor a curso
    @PostMapping("/asignarProfesorACurso/{usuarioId}/{cursoId}")
    public String asignarProfesorACurso(@PathVariable Integer usuarioId, @PathVariable Integer cursoId){
        return cursoService.asignarProfesor(usuarioId, cursoId);
    }

    //asignar  curso un estudiante
    @PostMapping("/{cursoId}/estudiantes/{estudianteId}")
    public ResponseEntity<String> asignarEstudiante(
        @PathVariable Integer cursoId,
        @PathVariable Integer estudianteId){
            String resultado =  cursoService.asignarEstudinteACurso(estudianteId, cursoId);
            if(resultado.startsWith("Error")){
                return ResponseEntity.badRequest().body(resultado);
            }
            return ResponseEntity.ok(resultado);
        }

}
