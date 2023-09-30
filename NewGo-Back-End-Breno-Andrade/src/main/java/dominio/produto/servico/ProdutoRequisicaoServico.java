package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import infraestrutura.produto.dao.ProdutoDAO;

public class ProdutoRequisicaoServico{
    private UtilProduto utilProduto = new UtilProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto requisitarProduto(ProdutoRequisicaoDto produtoDto){
        utilProduto.verificarHash(produtoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHash(produtoDto.getHash()));
    }



}
