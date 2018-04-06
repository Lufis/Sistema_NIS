package br.ba.devry.nis.dao;

import br.ba.devry.nis.model.Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

    private List<Login> list;
    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private String query;

    public void connect() {

        String stringServer = "jdbc:mysql://localhost:3306/NISRuyBarbosa?useSSL=false";

        String user = "root";
        String password = "daniel12";
        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver);

            this.con = DriverManager.getConnection(stringServer, user, password);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    public boolean isConnected() {
        if (this.con != null) {
            return true;
        } else {
            return false;
        }

    }

    public void closeConnection() {
        try {
            this.con.close();
            this.stmt.close();
            this.rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Boolean findUser(String user) {

        try {
            query = "SELECT 1 FROM login where USUARIO = ?";
            stmt = this.con.prepareStatement(query);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
        return false;
    }

    public Boolean confirmPassword(String password) {
        try {
            query = "SELECT 1 FROM login where SENHA = ?";
            stmt = this.con.prepareStatement(query);
            stmt.setString(1, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
        return false;
    }

    public List<Login> getLoginList() {
      
        List<Login> listaLogin = new ArrayList<>();
        try {

            stmt = con.prepareStatement("select * from login");
            rs = stmt.executeQuery();

            while (rs.next()) {
               
                 Login login = new Login();
                login.setId(rs.getInt("ID"));
                login.setUsuario(rs.getString("usuario"));
                login.setSenha(rs.getString("senha"));
                listaLogin.add(login);

            }
            
        } catch (Exception e) {
        }
        finally {  
            try {  
                stmt.close();  
                rs.close();  
                con.close();  
            } catch (Exception e) {  
              
            }  
        }
     
return listaLogin;
    }
}
