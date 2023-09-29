package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoRequisicaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import dominio.produto.excecao.ProdutoRequisicaoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;

import java.util.UUID;

public class ProdutoRequisicaoServico{
    private ProdutoServico produtoServico = new ProdutoServico();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto requisitarProduto(ProdutoRequisicaoDto produtoDto){
        produtoServico.verificarHash(produtoDto.getHash());

        return produtoMapper.entidadeParaRetornoDto(produtoDAO.buscarPorHash(produtoDto.getHash()));
    }



}
