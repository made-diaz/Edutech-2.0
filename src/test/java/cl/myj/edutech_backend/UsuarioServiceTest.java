package cl.myj.edutech_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.myj.edutech_backend.model.Persona;
import cl.myj.edutech_backend.model.Rol;
import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.repository.PersonaRepository;
import cl.myj.edutech_backend.repository.RolRepository;
import cl.myj.edutech_backend.repository.UsuarioRepository;
import cl.myj.edutech_backend.service.UsuarioService;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // Test para crear un usuario
    @Test
    void crearUsuarioTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setClave("12345");
        usuario.setCorreo("juan@example.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        String resultado = usuarioService.crearUsuario(usuario);
        assertEquals("Usuario creado correctamente!", resultado);
        verify(usuarioRepository).save(usuario);
    }

    // Test para actualizar un usuario
    @Test
    void actualizarUsuarioTest() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1);
        usuarioExistente.setNombre("Juan Pérez");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1);
        usuarioActualizado.setNombre("Juan Pérez Actualizado");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        String resultado = usuarioService.actualizarUsuario(usuarioActualizado);
        assertEquals("Usuario actualizado correctamente", resultado);
        verify(usuarioRepository).save(usuarioExistente);
    }

    // Test para actualizar un usuario inexistente
    @Test
    void actualizarUsuarioNoExistenteTest() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1);
        usuarioActualizado.setNombre("Juan Pérez");

        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        String resultado = usuarioService.actualizarUsuario(usuarioActualizado);
        assertEquals("Error: usuario no encontrado", resultado);
    }

    // Test para desactivar un usuario
    @Test
    void desactivarUsuarioTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setEstado(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        String resultado = usuarioService.desactivarUsuario(1);
        assertEquals("Usuario desactivado!", resultado);
        verify(usuarioRepository).save(usuario);
    }

    // Test para eliminar un usuario
    @Test
    void eliminarUsuarioTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        String resultado = usuarioService.eliminarUsuario(1);
        assertEquals("Usuario eliminado!", resultado);
        verify(usuarioRepository).delete(usuario);
    }

    // Test para asignar un rol a un usuario
    @Test
    void asignarRolTest() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan Pérez");

        Rol rol = new Rol();
        rol.setId(1);
        rol.setNombre("Administrador");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));


        String resultado = usuarioService.asignarRol(1, 1);


        assertEquals("Rol asignado!", resultado);


        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        Usuario usuarioGuardado = captor.getValue();
        assertNotNull(usuarioGuardado.getRol());
        assertEquals("Administrador", usuarioGuardado.getRol().getNombre());
        assertEquals(1, usuarioGuardado.getRol().getId());
    }


    // Test para listar los usuarios
    @Test
    void listarUsuariosTest() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan Pérez");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Ana Gómez");

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);

        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        List<Usuario> resultado = usuarioService.listar();
        assertEquals(2, resultado.size());
    }

    // Test para listar roles
    @Test
    void listarRolesTest() {
        Rol rol1 = new Rol();
        rol1.setNombre("Administrador");

        Rol rol2 = new Rol();
        rol2.setNombre("Profesor");

        List<Rol> listaRoles = new ArrayList<>();
        listaRoles.add(rol1);
        listaRoles.add(rol2);

        when(rolRepository.findAll()).thenReturn(listaRoles);

        List<Rol> resultado = usuarioService.listarRoles();
        assertEquals(2, resultado.size());
    }

    // Test para listar personas
    @Test
    void listarPersonasTest() {
        Persona persona1 = new Persona();
        persona1.setNombre("Carlos Pérez");

        Persona persona2 = new Persona();
        persona2.setNombre("Laura García");

        List<Persona> listaPersonas = new ArrayList<>();
        listaPersonas.add(persona1);
        listaPersonas.add(persona2);

        when(personaRepository.findAll()).thenReturn(listaPersonas);

        List<Persona> resultado = usuarioService.listarPersonas();
        assertEquals(2, resultado.size());
    }
}
