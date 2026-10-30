package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String usuario = System.getenv("DB_USER");
        String senha = System.getenv("DB_PASSWORD");
        if (url == null || url.isBlank() || usuario == null || usuario.isBlank()
                || senha == null || senha.isBlank()) {
            throw new IllegalStateException("Variáveis DB_URL, DB_USER e DB_PASSWORD não configuradas");
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}
