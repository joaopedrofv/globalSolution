package gs.example.globalsolution.model.statusDispositivos;

import gs.example.globalsolution.model.cadastroDispositivos.CadastroDispositivos;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "STATUS_DISPOSITIVOS")
@Data
public class StatusDispositivos {
    @Id
    @Column(name = "ID_STATUS")
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private CadastroDispositivos dispositivos;
    @Column(name = "STATUS")
    private Status status;
    @Column(name = "DATA_STATUS")
    private Timestamp dataStatus;
}
