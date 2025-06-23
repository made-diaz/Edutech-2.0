package cl.myj.edutech_backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String clave;
    private String correo;
    private Boolean estado = false;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @OneToMany
    @JsonIgnore
    private List<Curso> cursos;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Rol rol;

    //relacion muchos a muchos con curso para estudiantes
    @ManyToMany
    @JoinTable(
        name = "usuario_curso",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonIgnore
    private List<Curso> cursosEstudiante = new ArrayList<>();

    
    //relacion como profesor
    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private List<Curso> cursoComoProfesor;

    @OneToMany
    @JsonIgnore
    private List<Incidencia> incidencias;

}
