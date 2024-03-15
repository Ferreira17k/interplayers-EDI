package br.com.ciamed.connectciamed.ConnectCiamed.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnDB {
    private static final String URL = "jdbc:oracle:thin:@//NINGUEM SABE O IP";
    private static final String USER = "NINGUEM SABE O LOGIN";
    private static final String PASSWORD = "NINGUEM SABE A SENHA";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Registre o driver JDBC Oracle (somente necessário uma vez)
            Class.forName("oracle.jdbc.OracleDriver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC Oracle não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        return connection;
    }
}
