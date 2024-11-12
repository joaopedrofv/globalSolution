package gs.example.globalsolution.model.faturas;

public enum StatusFatura {
    pago("Pago"),
    pendente("Pendente");
    private final String descricao;

    StatusFatura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
