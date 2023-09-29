package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import infraestrutura.produto.dao.ProdutoDAO;

public class ProdutoExclusaoServico {
    private ProdutoServico produtoServico = new ProdutoServico();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void excluirProduto(ProdutoRequisicaoDto produtoRequisicaoDto){
        produtoServico.verificarHash(produtoRequisicaoDto.getHash());
        produtoDAO.excluirProduto(produtoRequisicaoDto.getHash());
    }
}
