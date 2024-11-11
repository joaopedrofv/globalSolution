package gs.example.globalsolution.model.notificacoes;

public enum TipoNotificacao {
    alerta("alerta"),
    informativo("informativo"),
    erro("erro");

    private final String descricao;

    TipoNotificacao(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
