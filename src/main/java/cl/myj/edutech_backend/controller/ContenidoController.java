package cl.myj.edutech_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.myj.edutech_backend.model.Contenido;
import cl.myj.edutech_backend.service.ContenidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/contenido")
public class ContenidoController {
    @Autowired
    private ContenidoService contenidoService;

    //CREAR
    @PostMapping
    public ResponseEntity<String> crearContenido(@RequestBody Contenido contenido) {
        String resultado = contenidoService.crearContenido(contenido);
        if(resultado.equals("Contenido creado correctamente")) {
            return ResponseEntity.ok("Contenido con ID " + contenido.getId() + " creado correctamente!");
        }
        return ResponseEntity.badRequest().body(resultado);
    }
    //MOSTRAR
    @GetMapping
    public List<Contenido> listarContenido() {
        return contenidoService.listarContenido();
    }
    //ASIGNAR
    @PostMapping("/asignarCurso/{idContenido}/{idCurso}")
    public String asignarContenido(@PathVariable Integer idContenido, @PathVariable Integer idCurso) {
        return contenidoService.asignarContenido(idContenido, idCurso);
    }
}
