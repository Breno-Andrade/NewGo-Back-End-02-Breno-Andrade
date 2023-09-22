package aplicacao.produto.servlet;

import aplicacao.produto.dto.ProdutoInsercaoDto;
import aplicacao.produto.dto.ProdutoRetornoDto;
import com.google.gson.Gson;
import dominio.produto.servico.ProdutoServico;
import infraestrutura.ProdutoDAO;
import dominio.produto.entidade.Produto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


@WebServlet(name = "ProdutoControladora", urlPatterns = "/produtos")
public class ProdutoControladora extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(200);

        PrintWriter printer = resp.getWriter();

        UUID produtoHash = UUID.fromString(req.getParameter("hash"));
        printer.print(gson.toJson(produtoDAO.buscarPorHash(produtoHash)));
        printer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader bufferedReader = req.getReader();
        String atributos = null;

        while((atributos = bufferedReader.readLine()) != null) {
            stringBuffer.append(atributos);
        }

        ProdutoInsercaoDto produtoDto = gson.fromJson(stringBuffer.toString(), ProdutoInsercaoDto.class);

        ProdutoServico produtoServico = new ProdutoServico();
        ProdutoRetornoDto produtoRetornoDto = produtoServico.salvarNovoProduto(produtoDto);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(gson.toJson(produtoRetornoDto));
        printWriter.flush();
        resp.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setStatus(202);

        UUID produtoHash = UUID.fromString(req.getParameter("hash"));
        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader bufferedReader = req.getReader();
        String atributos = null;

        while((atributos = bufferedReader.readLine()) != null) {
            stringBuffer.append(atributos);
        }

        Produto produto = gson.fromJson(stringBuffer.toString(), Produto.class);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(gson.toJson(produtoDAO.atualizarProduto(produtoHash ,produto)));
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setStatus(202);

        UUID produtoHash = UUID.fromString(req.getParameter("hash"));

        BufferedReader bufferedReader = req.getReader();
        String prodResp = produtoDAO.excluirProduto(produtoHash);



        PrintWriter printWriter = resp.getWriter();

        if(prodResp == null) {
            resp.setStatus(404);
            printWriter.print("Produto não foi encontrado.");
            printWriter.flush();
        } else {
            printWriter.print(prodResp);
            printWriter.flush();
        }
    }
}
