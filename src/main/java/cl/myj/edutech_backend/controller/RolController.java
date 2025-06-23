package cl.myj.edutech_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.myj.edutech_backend.model.Rol;
import cl.myj.edutech_backend.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    //crear
    @PostMapping
    public String crearRol(@RequestBody Rol rol){
        return rolService.crearRol(rol);
    }

    //mostrar
    @GetMapping
    public List<Rol> listarRoles(){
        return rolService.listarRoles();
    }

    //asociaar rol a usuario
    @PostMapping("/asignar/{usuarioId}/{rolId}")
    public String asignarRol(@PathVariable Integer usuarioId, @PathVariable Integer rolId){
        return rolService.asociarRolAUsuario(usuarioId, rolId);
    }
}
