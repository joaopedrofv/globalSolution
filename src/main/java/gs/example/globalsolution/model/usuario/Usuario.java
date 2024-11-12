package gs.example.globalsolution.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "USUARIOS")
@Data
public class Usuario {
    @Id
    private long id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "ENDERECO")
    private String endereco;
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
}
