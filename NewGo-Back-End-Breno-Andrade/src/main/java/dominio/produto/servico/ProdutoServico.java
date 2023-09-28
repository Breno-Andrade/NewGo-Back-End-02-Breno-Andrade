package dominio.produto.servico;

import dominio.produto.excecao.ProdutoInsercaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;

public class ProdutoServico {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void precoNegativo(double preco) throws ProdutoInvalidoExcecao{
        if (doubleNegativo(preco)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.PRECO_NEGATIVO);
        }
    }

    public void quantidadeNegativo(double quantidade) throws ProdutoInvalidoExcecao{
        if (doubleNegativo(quantidade)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.QUANTIDADE_NEGATIVA);
        }
    }

    public void estoqueMinNegativo(double estoqueMin) throws ProdutoInvalidoExcecao{
        if (doubleNegativo(estoqueMin)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.ESTOQUE_MIN_NEGATIVO);
        }
    }

    public boolean doubleNegativo(double valor){
        return valor < 0.0;
    }
}
