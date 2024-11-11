package gs.example.globalsolution.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "FATURAS")
@Data
public class Faturas {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private CadastroDispositivos dispositivos;
    @Column(name = "VALOR_TOTAL")
    private int valorTotal;
    @Column(name = "DATA_VENCIMENTO")
    private Date dataVencimento;
    @Column(name = )
}
