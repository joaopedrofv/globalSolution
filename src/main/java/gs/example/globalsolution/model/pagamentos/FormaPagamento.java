package gs.example.globalsolution.model.pagamentos;

public enum FormaPagamento {
    cartao("Cartão"),
    pix("Pix"),
    debito("Débito");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
