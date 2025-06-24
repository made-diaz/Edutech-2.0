package cl.myj.edutech_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.myj.edutech_backend.model.Evaluacion;
import cl.myj.edutech_backend.repository.CursoRepository;
import cl.myj.edutech_backend.repository.EvaluacionRepository;
import cl.myj.edutech_backend.service.EvaluacionService;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    @Test
    void crearEvaluacionExitosoTest() {
        Evaluacion evaluacion = new Evaluacion();
        String resultado = evaluacionService.crearEvaluacion(evaluacion);

        assertEquals("Evaluacion creada correctamente", resultado);
        verify(evaluacionRepository).save(evaluacion);
    }

    @Test
    void listarEvaluacionTest() {
        List<Evaluacion> lista = new ArrayList<>();
        lista.add(new Evaluacion());
        lista.add(new Evaluacion());
        when(evaluacionRepository.findAll()).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.listarEvaluacion();

        assertEquals(2, resultado.size());
    }

    @Test
    void asignarEvaluacionErrorCursoNoDisponibleTest() {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(1);

        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(2)).thenReturn(Optional.empty());

        String resultado = evaluacionService.asignarEvaluacion(1, 2);

        assertEquals("Error: Curso no disponible", resultado);
    }
}
