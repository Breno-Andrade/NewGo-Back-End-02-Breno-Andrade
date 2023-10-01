package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.Util.UtilVerificacoesProduto;
import infraestrutura.produto.entidade.Produto;
import dominio.produto.excecao.ProdutoInsercaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;
import java.util.UUID;

public class ProdutoInsercaoServico {
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper conversorProduto = new ProdutoMapper();

    public ProdutoInsercaoServico() {
    }

    public ProdutoRetornoDto salvarNovoProduto(ProdutoInsercaoDto produtoInsercaoDto){
        verificacoesSalvamento(produtoInsercaoDto);

        Produto novoProduto = conversorProduto.insercaoDtoParaEntidade(produtoInsercaoDto);
        novoProduto.setHash(gerarHashUnico());
        novoProduto.setDtcreate(utilVerificacoesProduto.gerarTimestampAtual());

        return conversorProduto.entidadeParaRetornoDto(produtoDAO.inserirNovoProduto(novoProduto));
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
        utilVerificacoesProduto.precoNegativo(produtoDto.getPreco());
        utilVerificacoesProduto.quantidadeNegativo(produtoDto.getQuantidade());
        utilVerificacoesProduto.estoqueMinNegativo(produtoDto.getEstoque_min());
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
