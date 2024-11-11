package gs.example.globalsolution.model.pagamentos;

import gs.example.globalsolution.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "PAGAMENTOS")
@Data
public class Pagamentos {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @Column(name = "VALOR")
    private int valor;
    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;
    @Column(name = "FORMA_PAGAMENTO")
    private FormaPagamento formaPagamento;
    @Column(name = "STATUS_PAGAMENTO")
    private StatusPagamento statusPagamento;
}
