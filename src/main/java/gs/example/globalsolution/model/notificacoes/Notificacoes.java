package gs.example.globalsolution.model.notificacoes;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "NOTIFICACOES")
@Data
public class Notificacoes {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "MENSAGEM")
    private String mensagem;
    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipo;
    @Column(name = "DATA_ENVIO")
    private Date dataEnvio;
    @Column(name = "LIDA")
    private Boolean leitura;
}
