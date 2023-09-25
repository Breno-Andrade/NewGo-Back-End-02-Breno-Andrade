package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.entidade.Produto;
import dominio.produto.excecao.ProdutoInsercaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;

import java.util.UUID;

public class ProdutoInsercaoServico {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper conversorProduto = new ProdutoMapper();

    public ProdutoInsercaoServico() {
    }

    public ProdutoRetornoDto salvarNovoProduto(ProdutoInsercaoDto produtoInsercaoDto){
        verificacoesSalvamento(produtoInsercaoDto);

        Produto novoProduto = conversorProduto.insercaoDtoParaEntidade(produtoInsercaoDto);
        novoProduto.setHash(gerarHashUnico());

        Produto produtoSalvo = produtoDAO.inserirNovoProduto(novoProduto);

        return conversorProduto.entidadeParaRetornoDto(produtoSalvo);
    }

    public UUID gerarHashUnico(){
        UUID hash;
        do {
             hash = UUID.randomUUID();
        } while (produtoDAO.existeHash(hash));
        return hash;
    }

    public void verificacoesSalvamento(ProdutoInsercaoDto produtoDto){
        nomeDuplicado(produtoDto.getNome());
        nomeVazio(produtoDto.getNome());
        ean13Duplicado(produtoDto.getEan13());
        precoNegativo(produtoDto.getPreco());
        quantidadeNegativo(produtoDto.getQuantidade());
        estoqueMinNegativo(produtoDto.getEstoque_min());
    }

    public void nomeDuplicado(String nome){
        if (produtoDAO.existeNome(nome)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.NOME_DUPLICADO);
        }
    }

    public void nomeVazio(String nome){
        if (nome.trim().isEmpty()){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.NOME_VAZIO);
        }
    }

    public void ean13Duplicado(String ean13){
        if (produtoDAO.existeEan13(ean13)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.EAN13_DUPLICADO);
        }
    }

    public void precoNegativo(double preco){
        if (doubleNegativo(preco)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.PRECO_NEGATIVO);
        }
    }

    public void quantidadeNegativo(double quantidade){
        if (doubleNegativo(quantidade)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.QUANTIDADE_NEGATIVA);
        }
    }

    public void estoqueMinNegativo(double estoqueMin){
        if (doubleNegativo(estoqueMin)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.ESTOQUE_MIN_NEGATIVO);
        }
    }

    public boolean doubleNegativo(double valor){
        return valor < 0.0;
    }

}
