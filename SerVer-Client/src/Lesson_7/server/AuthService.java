package Lesson_7.server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        String sql = String.format("select nickname from users " +
                "where conect='false' and login = '%s' and password = '%s' ", login, pass);

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateConnectTrue(String login,String pass){
        String sql2= String.format("update users set conect='true' where password='%s' and login='%s'",pass,login);
        try {
            stmt.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateConnectFalse(String login,String pass){
        String sql2= String.format("update users set conect='false' where password='%s' and login='%s'",pass,login);
        try {
            stmt.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateConnectAll(){
        String sql2= String.format("update users set conect='false'");
        try {
            stmt.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean existsNick(String nick){
        String sql= String.format("select * from users where conect='true' and nickname='%s'",nick);
        ResultSet rs=null;
        Boolean result=false;
        try {
           rs=stmt.executeQuery(sql);
            if(rs.next()){
                result=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return result;
    }
}
