package dominio.produto.excecao;

public class ErroJson{
    private String erro;

    public ErroJson(String error) {
        this.erro = error;
    }

    public String getError() {
        return erro;
    }

    public void setError(String error) {
        this.erro = error;
    }
}

