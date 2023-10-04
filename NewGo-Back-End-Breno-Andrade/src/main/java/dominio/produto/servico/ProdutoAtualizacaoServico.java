package dominio.produto.servico;

import aplicacao.produto.dto.*;
import dominio.produto.Util.UtilVerificacoesProduto;
import dominio.produto.excecao.ProdutoAtualizacaoExcecao;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import infraestrutura.produto.dao.ProdutoDAO;
import infraestrutura.produto.entidade.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoAtualizacaoServico {
    private UtilVerificacoesProduto utilVerificacoesProduto = new UtilVerificacoesProduto();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoMapper produtoMapper = new ProdutoMapper();

    public ProdutoRetornoDto atualizarProduto(UUID hash, ProdutoAtualizacaoDto produtoDto){
        utilVerificacoesProduto.verificarHash(hash);
        Produto produtoTemp = produtoDAO.buscarPorHash(hash);

        verificarLativo(produtoTemp);
        verificacaoModificacoesInvalidas(produtoDto, produtoTemp);

        Produto produto = produtoMapper.atualizacaoDtoParaEntidade(produtoDto, produtoTemp);
        produto.setDtupdate(utilVerificacoesProduto.gerarTimestampAtual());

        produtoDAO.atualizarProduto(hash, produto);

        produto = produtoDAO.buscarPorHash(hash);
        return produtoMapper.entidadeParaRetornoDto(produto);
    }

    public ProdutoRetornoDto atualizarEstoque(ProdutoAtualizarEstoqueDto produtoDto){
        utilVerificacoesProduto.verificarHash(produtoDto.getHash());
        Produto produto = produtoDAO.buscarPorHash(produtoDto.getHash());
        verificarEstoqueNegativo(produtoDto, produto);

        produto.setQuantidade(produtoDto.getQuantidade() + produto.getQuantidade());
        produto.setDtupdate(utilVerificacoesProduto.gerarTimestampAtual());

        produtoDAO.alterarEstoque(produto);

        return produtoMapper.entidadeParaRetornoDto(produto);
    }

    public List<Object> atualizarLoteEstoque(List<ProdutoAtualizarEstoqueDto> produtosAtualizarEstoqueDto){
        List<Object> produtosLoteRetornoDto = new ArrayList<>();

        for(ProdutoAtualizarEstoqueDto produtoDto : produtosAtualizarEstoqueDto){
            try{
                produtosLoteRetornoDto.add(
                        produtoMapper.retornoDtoParaLoteRetornoDto(
                                atualizarEstoque((produtoDto)),
                                "sucesso",
                                "Produto atualizado no banco"
                        ));
            } catch (ProdutoInvalidoExcecao e){
                produtosLoteRetornoDto.add(
                        produtoMapper.atualizarEstoqueDtoParaLoteErroRetornoDto(
                                produtoDto,
                                "Erro",
                                e.getMessage()
                        )
                );
            }
        }
        return produtosLoteRetornoDto;
    }

    public void verificacaoModificacoesInvalidas(ProdutoAtualizacaoDto produtoDto, Produto produto){
        utilVerificacoesProduto.precoNegativo(produtoDto.getPreco());
        utilVerificacoesProduto.quantidadeNegativo(produtoDto.getQuantidade());
        utilVerificacoesProduto.estoqueMinNegativo(produtoDto.getEstoque_min());
        verificarAlteracaoId(produtoDto, produto);
        verificarAlteracaoHash(produtoDto, produto);
        verificarAlteracaoNome(produtoDto, produto);
        verificarAlteracaoEan13(produtoDto, produto);
        verificarAlteracaoDtcreate(produtoDto, produto);
        verificarAlteracaoDtupdate(produtoDto, produto);
    }

    public void verificarEstoqueNegativo(ProdutoAtualizarEstoqueDto produtoDto, Produto produto){
        if (produto.getQuantidade() + produtoDto.getQuantidade() < 0){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.ESTOQUE_NEGATIVO);
        }
    }

    public void verificarAlteracaoId(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getId() != 0 && produtoDto.getId() != produto.getId()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.ID_INVALIDO);
        }
    }

    public void verificarAlteracaoHash(ProdutoAtualizacaoDto produtoDto, Produto produto){
        if (produtoDto.getHash() != null && produtoDto.getHash() != produto.getHash()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.HASH_INVALIDO);
        }
    }

    public void verificarAlteracaoNome(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getNome() != null && !produtoDto.getNome().equals(produto.getNome())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.NOME_INVALIDO);
        }
    }

    public void verificarAlteracaoEan13(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getEan13() != null && !produtoDto.getEan13().equals(produto.getEan13())){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.EAN13_INVALIDO);
        }
    }

    public void verificarAlteracaoDtcreate(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getDtcreate() != null && produtoDto.getDtcreate() != produto.getDtcreate()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.DTCREATE_INVALIDO);
        }
    }

    public void verificarAlteracaoDtupdate(ProdutoAtualizacaoDto produtoDto , Produto produto){
        if (produtoDto.getDtupdate() != null && produtoDto.getDtupdate() != produto.getDtupdate()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.DTUPDATE_INVALIDO);
        }
    }

    private void verificarLativo(Produto produto){
        if(!produto.isLativo()){
            throw new ProdutoInvalidoExcecao(ProdutoAtualizacaoExcecao.LATIVO_INVALIDO);
        }
    }
}
