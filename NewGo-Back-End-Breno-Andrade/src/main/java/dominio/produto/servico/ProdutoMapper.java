package dominio.produto.servico;

import aplicacao.produto.dto.*;
import infraestrutura.produto.entidade.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoMapper {
    public Produto insercaoDtoParaEntidade(ProdutoInsercaoDto produtoDto) {
        return new Produto(
                produtoDto.getNome(),
                produtoDto.getDescricao(),
                produtoDto.getEan13(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min()
        );
    }

    public Produto atualizacaoDtoParaEntidade(ProdutoAtualizacaoDto produtoDto, Produto informacoesBanco){
        return new Produto(
                informacoesBanco.getId(),
                informacoesBanco.getHash(),
                informacoesBanco.getNome(),
                produtoDto.getDescricao(),
                informacoesBanco.getEan13(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min(),
                informacoesBanco.getDtcreate(),
                informacoesBanco.getDtupdate(),
                informacoesBanco.isLativo()
        );
    }

    public ProdutoRetornoDto entidadeParaRetornoDto(Produto produto){
        return new ProdutoRetornoDto(
                produto.getId(),
                produto.getHash(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getEan13(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getEstoque_min(),
                produto.getDtcreate(),
                produto.getDtupdate(),
                produto.isLativo()
        );
    }

    public List<ProdutoRetornoDto> listaEntidadeParaListaRetorno(List<Produto> produtos){
        List<ProdutoRetornoDto> produtosDto = new ArrayList<>();
        for (Produto produto : produtos){
            produtosDto.add(entidadeParaRetornoDto(produto));
        }
        return produtosDto;
    }

    public ProdutoLoteRetornoDto retornoDtoParaLoteRetornoDto(ProdutoRetornoDto produtoDto, String status, String mensagem){
        return new ProdutoLoteRetornoDto(
                produtoDto.getId(),
                produtoDto.getHash(),
                produtoDto.getNome(),
                produtoDto.getDescricao(),
                produtoDto.getEan13(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min(),
                produtoDto.getDtcreate(),
                produtoDto.getDtupdate(),
                produtoDto.isLativo(),
                status,
                mensagem
        );
    }

    public ProdutoLoteErroRetornoDto insercaoDtoParaLoteErroRetornoDto(ProdutoInsercaoDto produtoDto, String status, String mensagem){
        return new ProdutoLoteErroRetornoDto(
                produtoDto.getNome(),
                produtoDto.getDescricao(),
                produtoDto.getEan13(),
                produtoDto.getPreco(),
                produtoDto.getQuantidade(),
                produtoDto.getEstoque_min(),
                status,
                mensagem
        );
    }
}
