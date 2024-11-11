package gs.example.globalsolution.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "CADASTRO_DISPOSITIVOS")
@Data
public class CadastroDispositivos {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "TIPO_DISPOSITVO")
    private String tipoDispositivo;
    @Column(name = "DATA_INSTALACAO")
    private Date dataInstalacao;
}
