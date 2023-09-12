package dados;

import java.sql.Connection;
import java.sql.DriverManager;

public class GeradorConexao {

    private final String endereco = "jdbc:postgresql://localhost:5432/backend02";
    private final String usuario = "postgres";
    private final String senha = "123";

    public Connection gerarConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(endereco, usuario, senha);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
