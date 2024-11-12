package gs.example.globalsolution.model.relatoriosConsumo;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "RELATORIOS_CONSUMO")
@Data
public class RelatoriosConsumo {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "PERIODO_INICIO")
    private Date periodoInicio;
    @Column(name = "PERIODO_FIM")
    private Date periodoFim;
    @Column(name = "TOTAL_CONSUMIDO_KWH")
    private int totalConsumidoKWH;
    @Column(name = "TOTAL_PAGO")
    private int totalPago;
    @Column(name = "ANALISE_TEXTO")
    private String analiseTexto;
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
}
