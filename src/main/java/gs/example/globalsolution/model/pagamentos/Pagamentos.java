package gs.example.globalsolution.model.pagamentos;

import gs.example.globalsolution.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "PAGAMENTOS")
@Data
public class Pagamentos {
    @Id
    @Column(name = "ID_PAGAMENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "VALOR")
    private Long valor;

    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGAMENTO")
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_PAGAMENTO")
    private StatusPagamento statusPagamento;
}
