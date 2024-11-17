package gs.example.globalsolution.model.cadastroDispositivos;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "CADASTRO_DISPOSITIVOS")
@Data
public class CadastroDispositivos {
    @Id
    @Column(name = "ID_DISPOSITIVO")
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "TIPO_DISPOSITIVO")
    private String tipoDispositivo;
    @Column(name = "DATA_INSTALACAO")
    private Date dataInstalacao;
}
