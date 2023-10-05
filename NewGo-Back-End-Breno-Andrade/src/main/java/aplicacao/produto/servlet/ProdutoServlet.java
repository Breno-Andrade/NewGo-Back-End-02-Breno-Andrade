package aplicacao.produto.servlet;

import aplicacao.produto.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dominio.produto.excecao.ErroJson;
import dominio.produto.excecao.ProdutoInvalidoExcecao;
import dominio.produto.servico.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@WebServlet("/produtos/*")
public class ProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        BufferedReader bufferedReader = req.getReader();
        StringBuffer stringBuffer = new StringBuffer();

        String atributos;
        while((atributos = bufferedReader.readLine()) != null) {
            stringBuffer.append(atributos);
        }
        try {
            String urlRequisicao = req.getRequestURI();
            String[] partesDaURL = urlRequisicao.split("/");
            if (partesDaURL.length == 5 && partesDaURL[4].equalsIgnoreCase("alterar-lativo")) {
                ProdutoRequisicaoDto produtoRequisicaoDto = new ProdutoRequisicaoDto(
                        UUID.fromString(partesDaURL[3].replace("/", ""))
                );

                ProdutoLativoDto produtoLativoDto = gson.fromJson(stringBuffer.toString(), ProdutoLativoDto.class);

                ProdutoLativoServico produtoServico = new ProdutoLativoServico();
                PrintWriter printWriter = resp.getWriter();
                printWriter.print(gson.toJson(produtoServico.alterarLativo(produtoRequisicaoDto.getHash(), produtoLativoDto)));
                printWriter.flush();
            }
        } catch(ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        ProdutoInsercaoServico produtoInsercaoServico = new ProdutoInsercaoServico();
        ProdutoAtualizacaoServico produtoAtualizacaoServico = new ProdutoAtualizacaoServico();
        PrintWriter printWriter = resp.getWriter();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = req.getReader();
            String atributos;

            while((atributos = bufferedReader.readLine()) != null) {
                stringBuffer.append(atributos);
            }
            String[] url = req.getRequestURI().split("/");
            if (url.length == 4){
                if (url[3].equalsIgnoreCase("inserir-lote")){
                    Type produtoInsercaoType = new TypeToken<List<ProdutoInsercaoDto>>() {}.getType();
                    List<ProdutoInsercaoDto> produtosInsercaoDto = gson.fromJson(stringBuffer.toString(), produtoInsercaoType);
                    printWriter.print(gson.toJson(produtoInsercaoServico.salvarNovosProdutos(produtosInsercaoDto)));
                }
                if (url[3].equalsIgnoreCase("atualizar-estoque")){
                    Type produtoAtualizarEstoqueType = new TypeToken<List<ProdutoAtualizarEstoqueDto>>() {}.getType();
                    List<ProdutoAtualizarEstoqueDto> produtosAtualizarEstoque = gson.fromJson(stringBuffer.toString(), produtoAtualizarEstoqueType);
                    printWriter.print(gson.toJson(produtoAtualizacaoServico.atualizarLoteEstoque(produtosAtualizarEstoque)));
                }
                if (url[3].equalsIgnoreCase("atualizar-preco")){
                    Type produtoAtualizarPrecoType = new TypeToken<List<ProdutoAtualizarPrecoDto>>() {}.getType();
                    List<ProdutoAtualizarPrecoDto> produtosAtualizarPreco = gson.fromJson(stringBuffer.toString(), produtoAtualizarPrecoType);
                    printWriter.print(gson.toJson(produtoAtualizacaoServico.atualizarPrecoLote(produtosAtualizarPreco)));
                }
                resp.setStatus(200);
                return;
            }
            if (url.length == 3) {
                ProdutoInsercaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoInsercaoDto.class);
                ProdutoRetornoDto produtoRetornoDto = produtoInsercaoServico.salvarNovoProduto(produtoDto);
                printWriter.print(gson.toJson(produtoRetornoDto));
                printWriter.flush();
            }
            resp.setStatus(201);
        } catch (ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        ProdutoRequisicaoServico produtoServico = new ProdutoRequisicaoServico();
        PrintWriter printer = resp.getWriter();

        String[] url = req.getRequestURI().split("/");
        String filtro = req.getParameter("lativo");
        String filtro2 = req.getParameter("estoque-menor-minimo");
        try{
            //Consulta em lote.
            if (url.length == 3){
                if (url[2].equalsIgnoreCase("produtos")){
                    if (Boolean.parseBoolean(filtro)){
                        printer.print(gson.toJson(produtoServico.requisitarTodosProdutosAtivos()));
                        return;
                    }
                    if (!Boolean.parseBoolean(filtro) && filtro != null){
                        printer.print(gson.toJson(produtoServico.requisitarTodosProdutosInativos()));
                        return;
                    }
                    if(filtro2 != null){
                        if (Boolean.parseBoolean(filtro2)){
                            printer.print(gson.toJson(produtoServico.requisitarProdutosEstoqueMenorMinimo()));
                            return;
                        }
                    }
                    printer.print(gson.toJson(produtoServico.requisitarTodosProdutos()));
                }
            }
            // Consulta especifica.
            if (url.length >= 4){
                ProdutoRequisicaoDto produtoDto = new ProdutoRequisicaoDto(UUID.fromString(url[3]));
                if (url.length == 5){
                    if (url[4].equalsIgnoreCase("ativo")){
                        printer.print(gson.toJson(produtoServico.requisitarProdutoAtivo(produtoDto)));
                        return;
                    }
                }
                printer.print(gson.toJson(produtoServico.requisitarProduto(produtoDto)));
            }

            printer.flush();
            resp.setStatus(200);
        } catch (ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        try {
            BufferedReader bufferedReader = req.getReader();
            StringBuffer stringBuffer = new StringBuffer();
            String atributos;
            while((atributos = bufferedReader.readLine()) != null) {
                stringBuffer.append(atributos);
            }

            ProdutoAtualizacaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoAtualizacaoDto.class);

            String urlRequisicao = req.getRequestURI();
            String[] partesDaURL = urlRequisicao.split("/");

            UUID produtoHash = null;

            if (partesDaURL.length >= 4){
                produtoHash = UUID.fromString(partesDaURL[3].replace("/", ""));
            }

            PrintWriter printWriter = resp.getWriter();
            ProdutoAtualizacaoServico produtoServico = new ProdutoAtualizacaoServico();
            printWriter.print(gson.toJson(produtoServico.atualizarProduto(produtoHash, produtoDto)));
            printWriter.flush();
            resp.setStatus(202);


        } catch(ProdutoInvalidoExcecao e){
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        try {
            ProdutoRequisicaoDto produtoDto = new ProdutoRequisicaoDto(
                    UUID.fromString(req.getPathInfo().replaceAll("/", ""))
            );

            ProdutoExclusaoServico produtoServico = new ProdutoExclusaoServico();
            PrintWriter printer = resp.getWriter();
            printer.print(gson.toJson(produtoServico.excluirProduto(produtoDto)));
            printer.flush();
            resp.setStatus(202);
        } catch (ProdutoInvalidoExcecao e) {
            resp.getWriter().write(gson.toJson(new ErroJson(e.getMessage())));
            resp.setStatus(404);
        }
    }
}
