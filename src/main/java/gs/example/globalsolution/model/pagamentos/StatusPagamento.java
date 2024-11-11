package gs.example.globalsolution.model.pagamentos;

public enum StatusPagamento {
    pago("Pago"),
    pendente("Pendente");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
