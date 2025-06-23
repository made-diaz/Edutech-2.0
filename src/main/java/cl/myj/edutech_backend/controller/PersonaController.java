package cl.myj.edutech_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.myj.edutech_backend.model.Persona;
import cl.myj.edutech_backend.service.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;


    //crear
    @PostMapping
    public String crearPersona(@RequestBody Persona persona){
        return personaService.crearPersona(persona);
    }

    //Mostrar
    @GetMapping
    public List<Persona> listarPersonas(){
        return personaService.listarPersonas();
    }

    //Asignar Usuario
    @PostMapping("/asignarUsuario/{idUsuario}/{idPersona}")
        public  ResponseEntity<String> asignarUsuario(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idPersona){
            try{
                String resultado = personaService.asignarUsuario(idUsuario, idPersona);
                if(resultado.startsWith("Error")){
                    return ResponseEntity.badRequest().body(resultado);
                }
                return ResponseEntity.ok(resultado);
            } catch(Exception e){
                return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
            }
        
    }
}
