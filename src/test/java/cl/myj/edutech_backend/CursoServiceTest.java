package cl.myj.edutech_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.model.Rol;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.CursoRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;
import cl.myj.edutech_backend.service.CursoService;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    CursoRepository cursoRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    CursoService cursoService;

    @Test
    void crearCursoExitosoTest() {
        Curso curso = new Curso();
        curso.setNombre("Matemática");

        String resultado = cursoService.crearCurso(curso);

        assertEquals("Curso creado correctamente", resultado);
        verify(cursoRepository).save(curso);
    }

    @Test
    void crearCursoErrorTest() {
        String resultado = cursoService.crearCurso(null);

        assertEquals("Error: Curso no ha podido ser creado", resultado);
        verify(cursoRepository, never()).save(any());
    }

    @Test
    void listarCursoTest() {
        Curso c1 = new Curso();
        c1.setNombre("Matemática");
        Curso c2 = new Curso();
        c2.setNombre("Lenguaje");

        List<Curso> lista = new ArrayList<>();
        lista.add(c1); lista.add(c2);

        when(cursoRepository.findAll()).thenReturn(lista);

        List<Curso> resultado = cursoService.listarCurso();

        assertEquals(2, resultado.size());
    }

    @Test
    void asignarProfesorOkTest() {
        Usuario profe = new Usuario();
        profe.setId(1);
        Rol rol = new Rol();
        rol.setNombre("Profesor");
        profe.setRol(rol);

        Curso curso = new Curso();
        curso.setId(1);
        curso.setNombre("Matemática");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(profe));
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarProfesor(1, 1);

        assertEquals("Profesor 'null' asignado correctamente al curso 'Matemática'", resultado);
        verify(cursoRepository).save(curso);
    }


    @Test
    void asignarProfesorNoEsProfesorTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        Rol rol = new Rol();
        rol.setNombre("Estudiante"); 
        usuario.setRol(rol);

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(new Curso()));

        String resultado = cursoService.asignarProfesor(1, 2);

        assertEquals("El usuario no tiene rol de profesor", resultado);
    }

    @Test
    void asignarProfesorYaAsignadoTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        Rol rol = new Rol();
        rol.setNombre("Profesor");
        usuario.setRol(rol);

        Curso curso = new Curso();
        curso.setId(2);
        curso.setUsuario(usuario); // ya asignado

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarProfesor(1, 2);

        assertEquals("El profesor ya esta asignado al curso", resultado);
    }

    @Test
    void asignarEstudianteACursoExitosoTest() {
        Usuario est = new Usuario();
        est.setId(1);
        Rol rol = new Rol();
        rol.setNombre("Estudiante");
        est.setRol(rol);
        est.setCursosEstudiante(new ArrayList<>());

        Curso curso = new Curso();
        curso.setId(2);
        curso.setEstudiantes(new ArrayList<>());

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(est));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarEstudinteACurso(1, 2);

        assertEquals("Asignacion exitosa", resultado);
        verify(usuarioRepository).save(est);
        verify(cursoRepository).save(curso);
    }

    @Test
    void asignarEstudianteNoEsEstudianteTest() {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        rol.setNombre("Profesor"); // No es estudiante
        usuario.setRol(rol);

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(new Curso()));

        String resultado = cursoService.asignarEstudinteACurso(1, 2);

        assertEquals("El usuario no es estudiante", resultado);
    }

    @Test
    void asignarEstudianteYaInscritoTest() {
        Usuario est = new Usuario();
        est.setId(1);
        Rol rol = new Rol();
        rol.setNombre("Estudiante");
        est.setRol(rol);
        est.setCursosEstudiante(new ArrayList<>());

        Curso curso = new Curso();
        curso.setId(2);
        curso.setEstudiantes(new ArrayList<>());

        est.getCursosEstudiante().add(curso); // Ya está inscrito

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(est));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarEstudinteACurso(1, 2);

        assertEquals("El estudiante ya esta inscrito en este curso", resultado);
    }


    

    
}
