package cl.myj.edutech_backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sigla;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //relacion con profesor
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Usuario profesor;

    //relacion con estudiante
    @ManyToMany(mappedBy = "cursosEstudiante")
    @JsonIgnore
    private List<Usuario> estudiantes;

    @OneToMany
    @JsonIgnore
    private List<Contenido> contenido;

    @OneToMany
    @JsonIgnore
    private List<Evaluacion> evaluacion;

}
