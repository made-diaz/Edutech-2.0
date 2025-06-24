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

import cl.myj.edutech_backend.model.Persona;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.PersonaRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;
import cl.myj.edutech_backend.service.PersonaService;

@ExtendWith(MockitoExtension.class)
class PersonaServiceTest {

    @Mock
    PersonaRepository personaRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    PersonaService personaService;

    @Test
    void crearPersonaExitosoTest() {
        Persona persona = new Persona();

        String resultado = personaService.crearPersona(persona);

        assertEquals("Persona creado correctamente", resultado);
        verify(personaRepository).save(persona);
    }

    @Test
    void crearPersonaErrorTest() {
        String resultado = personaService.crearPersona(null);

        assertEquals("Error: La Persona no puedo ser creada", resultado);
        verify(personaRepository, never()).save(any());
    }

    @Test
    void listarPersonasTest() {
        List<Persona> lista = new ArrayList<>();
        lista.add(new Persona());
        lista.add(new Persona());

        when(personaRepository.findAll()).thenReturn(lista);

        List<Persona> resultado = personaService.listarPersonas();

        assertEquals(2, resultado.size());
    }

    @Test
    void asignarUsuarioExitosoTest() {
        Persona persona = new Persona();
        persona.setId(1);

        Usuario usuario = new Usuario();
        usuario.setId(2);

        when(personaRepository.findById(1)).thenReturn(Optional.of(persona));
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));

        String resultado = personaService.asignarUsuario(2, 1);

        assertEquals("Usuario asignado!", resultado);
        verify(personaRepository).save(persona);
    }

    @Test
    void asignarUsuarioErrorPersonaNoEncontradaTest() {
        when(personaRepository.findById(99)).thenReturn(Optional.empty());

        String resultado = personaService.asignarUsuario(1, 99);

        assertEquals("Error: Persona no encontrada con ID: 99", resultado);
    }

    @Test
    void asignarUsuarioErrorUsuarioNoEncontradoTest() {
        Persona persona = new Persona();
        persona.setId(1);

        when(personaRepository.findById(1)).thenReturn(Optional.of(persona));
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        String resultado = personaService.asignarUsuario(99, 1);

        assertEquals("Error: Usuario no encontrado con ID: 99", resultado);
    }
}
