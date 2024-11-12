package gs.example.globalsolution.model.statusDispositivos;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "STATUS_DISPOSITIVOS")
@Data
public class StatusDispositivos {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private CadastroDispositivos dispositivos;
    @Column(name = "STATUS_DISPOSITIVO")
    private
    @Column(name = "DATA_STATUS")
    private
}
