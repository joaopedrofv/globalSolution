package gs.example.globalsolution.model.consumoEnergia;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "CONSUMO_ENERGIA")
@Data
public class ConsumoEnergia {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private CadastroDispositivos dispositivos;
    @Column(name = "DATA_HORA")
    private Date dataHora;
    @Column(name = "CONSUMO_KWH")
    private int consumoKWH;
    @Column(name = "PRECO_KWH")
    private int precoKWH;
}
