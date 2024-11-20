package gs.example.globalsolution.model.faturas;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "FATURAS")
@Data
public class Faturas {
    @Id
    @Column(name = "ID_FATURA")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private CadastroDispositivos dispositivos;
    @Column(name = "VALOR_TOTAL")
    private Long valorTotal;
    @Column(name = "DATA_VENCIMENTO")
    private Date dataVencimento;
    @Column(name = "DATA_EMISSAO")
    private Date dataEmissao;
    @Column(name = "STATUS")
    private StatusFatura statusFatura;
}
