package cl.myj.edutech_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import cl.myj.edutech_backend.model.Usuario;
import cl.myj.edutech_backend.service.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/admin/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        String resultado = usuarioService.crearUsuario(usuario);
        if(resultado.equals("Usuario creado correctamente!")) {
            return ResponseEntity.ok("Usuario con ID " + usuario.getId() + " creado correctamente!");
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @PostMapping("/admin/actualizar")
    public ResponseEntity<String> actualizarUsuario(@RequestBody Usuario usuarioActualizado) {
        String resultado = usuarioService.actualizarUsuario(usuarioActualizado);
        if(resultado.equals("Usuario actualizado!")) {
            return ResponseEntity.ok("Usuario con ID " + usuarioActualizado.getId() + " actualizado correctamente!");
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @PostMapping("/admin/desactivar/{id}")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Integer id) {
        String resultado = usuarioService.desactivarUsuario(id);
        if(resultado.equals("Usuario desactivado!")) {
            return ResponseEntity.ok("Usuario con ID " + id + " desactivado correctamente!");
        }
        return ResponseEntity.badRequest().body(resultado);
    }

    @DeleteMapping("/admin/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        String resultado = usuarioService.eliminarUsuario(id);
        if (resultado.equals("Usuario eliminado!")) {
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente!");
        } 
        return ResponseEntity.badRequest().body(resultado);
        
    }

    @GetMapping
    public List<Usuario> listaUsuarios() {
        return usuarioService.listar();
    }
    
    


}
