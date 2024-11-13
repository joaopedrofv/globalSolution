package gs.example.globalsolution.model.statusDispositivos;

public enum Status {
    ligado("Ligado"),
    desligado("Desligado");
    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
