package br.ba.devry.nis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAO {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultset = null;

    public void connect() {

        String stringServer = "jdbc:mysql://localhost:3306/NISRuyBarbosa?useSSL=false";

        String user = "root";
        String password = "daniel12";
        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver);

            this.connection = DriverManager.getConnection(stringServer, user, password);
            this.statement = this.connection.createStatement();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    public boolean isConnected() {
        if (this.connection != null) {
            return true;
        } else {
            return false;
        }

    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Boolean userExists(String user) {
        try {
            String query = "SELECT 1 FROM login where USUARIO = '" + user + "';";
            this.resultset = this.statement.executeQuery(query);

            if (this.resultset.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
        return false;
    }

    public Boolean confirmPassword(String password) {
        try {
            String query = "SELECT 1 FROM login where SENHA = '" + password + "';";
            this.resultset = this.statement.executeQuery(query);

            if (this.resultset.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
        return false;
    }

}
