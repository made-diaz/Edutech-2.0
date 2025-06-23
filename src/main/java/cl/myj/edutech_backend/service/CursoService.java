package cl.myj.edutech_backend.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.CursoRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Crear
    public String crearCurso(Curso curso){
        if(curso != null){
            cursoRepository.save(curso);
            return "Curso creado correctamente";
        }
        return "Error: Curso no ha podido ser creado";
    }

    //listar curso
    public List<Curso> listarCurso(){
        return cursoRepository.findAll();
    }

    //asignar profesor a curso
    public String asignarProfesor(Integer usuarioId, Integer cursoId){
        if(usuarioId == null || cursoId == null){
            return "Error: Los IDs no pueden ser nulos";
        }
        try{
            if(!usuarioRepository.existsById(usuarioId)){
                return "El usuario ingresado no existe";
            }
            if(!cursoRepository.existsById(cursoId)){
                return "El curso ingresado no existe";
            }
            Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no disponible"));
            Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no disponible"));
            if(usuario.getRol() == null || !usuario.getRol().getNombre().equalsIgnoreCase("profesor")){
                return "El usuario no tiene rol de profesor";
            }
            if(curso.getUsuario() != null && curso.getUsuario().getId().equals(usuarioId)){
                return "El profesor ya esta asignado al curso";
            }
            curso.setProfesor(usuario);
            cursoRepository.save(curso);

            return String.format("Profesor '%s' asignado correctamente al curso '%s'",
                usuario.getNombre(),
                curso.getNombre());
        } catch(Exception e){
            return "Error inesperado al asignar el profesor: " + e.getMessage();
        }
              
    }

    //asignar estudiante a curso
    public String asignarEstudinteACurso(Integer estudianteID, Integer cursoId){
        if(estudianteID == null || cursoId == null){
            return "Error: Los IDs no pueden ser nulos";
        }
        try{
            if(!usuarioRepository.existsById(estudianteID)){
                return "El usuario no existe";
            }
            if(!cursoRepository.existsById(cursoId)){
                return "El curso no existe";
            }

            Usuario usuario = usuarioRepository.findById(estudianteID).get();
            Curso curso = cursoRepository.findById(cursoId).get();

            if(!"ESTUDIANTE".equalsIgnoreCase(usuario.getRol().getNombre())){
                return "El usuario no es estudiante";
            }

            if(usuario.getCursosEstudiante().contains(curso)){
                return "El estudiante ya esta inscrito en este curso";
            }
            

            usuario.getCursosEstudiante().add(curso);
            curso.getEstudiantes().add(usuario);
            usuarioRepository.save(usuario);
            cursoRepository.save(curso);

            return "Asignacion exitosa";

        }catch(Exception e){
            return "Error: " + e.getMessage();
        }
    }

}
