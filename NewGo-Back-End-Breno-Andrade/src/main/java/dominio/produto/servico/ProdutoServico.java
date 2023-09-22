package dominio.produto.servico;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import dominio.produto.entidade.Produto;
import infraestrutura.ProdutoDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ProdutoServico {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    public ProdutoServico() {
    }

    public ProdutoRetornoDto salvarNovoProduto(ProdutoInsercaoDto produtoInsercaoDto){
        verificacoesSalvamento(produtoInsercaoDto);

        Produto novoProduto = new Produto(
                produtoInsercaoDto.getNome(),
                produtoInsercaoDto.getDescricao(),
                produtoInsercaoDto.getEan13(),
                produtoInsercaoDto.getPreco(),
                produtoInsercaoDto.getQuantidade(),
                produtoInsercaoDto.getEstoque_min()
        );
        novoProduto.setHash(gerarHashUnico());

        Produto retornoProduto = produtoDAO.inserirNovoProduto(novoProduto);
        return new ProdutoRetornoDto(
                retornoProduto.getId(),
                retornoProduto.getHash(),
                retornoProduto.getNome(),
                retornoProduto.getDescricao(),
                retornoProduto.getEan13(),
                retornoProduto.getPreco(),
                retornoProduto.getQuantidade(),
                retornoProduto.getEstoque_min(),
                retornoProduto.getDtcreate(),
                retornoProduto.getDtupdate(),
                retornoProduto.isLativo()
        );
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
        ean13Duplicado(produtoDto.getEan13());
        precoNegativo(produtoDto.getPreco());
        quantidadeNegativo(produtoDto.getQuantidade());
        estoqueMinNegativo(produtoDto.getEstoque_min());
    }

    public void nomeDuplicado(String nome){
        if (produtoDAO.existeNome(nome)){
            throw new IllegalArgumentException("O nome do produto já existe, troque e tente novamente.");
        }
    }

    public void ean13Duplicado(String ean13){
        if (produtoDAO.existeEan13(ean13)){
            throw new IllegalArgumentException("O código ean13 do produto já existe, troque e tente novamente.");
        }
    }

    public void precoNegativo(double preco){
        if (doubleNegativo(preco)){
            throw new IllegalArgumentException("O preço não pode ser negativo, troque e tente novamente");
        }
    }

    public void quantidadeNegativo(double quantidade){
        if (doubleNegativo(quantidade)){
            throw new IllegalArgumentException("A quantidade não pode ser negativo, troque e tente novamente");
        }
    }

    public void estoqueMinNegativo(double estoqueMin){
        if (doubleNegativo(estoqueMin)){
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo, troque e tente novamente");
        }
    }

    public boolean doubleNegativo(double valor){
        return valor < 0.0;
    }

}
