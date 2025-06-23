package cl.myj.edutech_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.myj.edutech_backend.model.Incidencia;
import cl.myj.edutech_backend.service.IncidenciaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/incidencia")
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    //crear y asignar
    @PostMapping("/crearIncidencia/{idUsuario}")
    public String crearIncidencia(@RequestBody Incidencia incidencia, @PathVariable Integer idUsuario) {
        return incidenciaService.crearIncidencia(incidencia, idUsuario);
    }
    //mostrar
    @GetMapping
    public List<Incidencia> listarIncidencia() {
        return incidenciaService.listarIncidencia();
    }
}
