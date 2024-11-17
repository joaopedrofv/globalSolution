package gs.example.globalsolution.model.configuracoes;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "CONFIGURACOES")
@Data
public class Configuracoes {
    @Id
    @Column(name = "ID_CONFIGURACOES")
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "HORARIO_CORTE_INICIO")
    private Timestamp corteInicio;
    @Column(name = "HORARIO_CORTE_FIM")
    private Timestamp corteFim;
    @Column(name = "LIMITE_ALERTA")
    private Long alerta;
    @Column(name = "PREFERENCIAS")
    private String preferencias;
}
