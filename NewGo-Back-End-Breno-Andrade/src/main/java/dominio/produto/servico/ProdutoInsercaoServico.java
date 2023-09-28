package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.entidade.Produto;
import dominio.produto.excecao.ProdutoInsercaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;

import java.util.UUID;

public class ProdutoInsercaoServico {
    private ProdutoServico produtoServico = new ProdutoServico();
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
        produtoServico.precoNegativo(produtoDto.getPreco());
        produtoServico.quantidadeNegativo(produtoDto.getQuantidade());
        produtoServico.estoqueMinNegativo(produtoDto.getEstoque_min());
    }

    public void nomeDuplicado(String nome) throws ProdutoInvalidoExcecao {
        if (produtoDAO.existeNome(nome)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.NOME_DUPLICADO);
        }
    }

    public void nomeVazio(String nome) throws ProdutoInvalidoExcecao{
        if (nome.trim().isEmpty()){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.NOME_VAZIO);
        }
    }

    public void ean13Duplicado(String ean13) throws ProdutoInvalidoExcecao{
        if (produtoDAO.existeEan13(ean13)){
            throw new ProdutoInvalidoExcecao(ProdutoInsercaoExcecao.EAN13_DUPLICADO);
        }
    }
}
