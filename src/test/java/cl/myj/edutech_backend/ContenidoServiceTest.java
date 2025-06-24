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

import cl.myj.edutech_backend.model.Contenido;
import cl.myj.edutech_backend.model.Curso;
import cl.myj.edutech_backend.repository.ContenidoRepository;
import cl.myj.edutech_backend.repository.CursoRepository;
import cl.myj.edutech_backend.service.ContenidoService;

@ExtendWith(MockitoExtension.class)
class ContenidoServiceTest {

    @Mock
    private ContenidoRepository contenidoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private ContenidoService contenidoService;

    @Test
    void crearContenidoExitosoTest() {
        Contenido contenido = new Contenido();
        String resultado = contenidoService.crearContenido(contenido);

        assertEquals("Contenido creado correctamente", resultado);
        verify(contenidoRepository).save(contenido);
    }

    @Test
    void crearContenidoErrorTest() {
        String resultado = contenidoService.crearContenido(null);

        assertEquals("Error: Contenido no ha podido ser creado", resultado);
        verify(contenidoRepository, never()).save(any());
    }

    @Test
    void listarContenidoTest() {
        List<Contenido> lista = new ArrayList<>();
        lista.add(new Contenido());
        lista.add(new Contenido());
        when(contenidoRepository.findAll()).thenReturn(lista);

        List<Contenido> resultado = contenidoService.listarContenido();

        assertEquals(2, resultado.size());
    }

    @Test
    void asignarContenidoExitosoTest() {
        Contenido contenido = new Contenido();
        contenido.setId(1);
        Curso curso = new Curso();
        curso.setId(2);

        when(contenidoRepository.findById(1)).thenReturn(Optional.of(contenido));
        when(cursoRepository.findById(2)).thenReturn(Optional.of(curso));

        String resultado = contenidoService.asignarContenido(1, 2);

        assertEquals("Contenido asignado correctamente", resultado);
        verify(contenidoRepository).save(contenido);
    }

    @Test
    void asignarContenidoErrorTest() {
        when(contenidoRepository.findById(1)).thenReturn(Optional.empty());

        String resultado = contenidoService.asignarContenido(1, 2);

        assertEquals("Error: Contenido no disponible", resultado);
    }
}
