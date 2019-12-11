package ru.geekbrains.chat.server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode(); // 137
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void InserIntoToBlock(String nickblock, String nickusers){
        try {

            int idusers=0;
            int id_block=0;
            ResultSet st=stmt.executeQuery("select id from main where nickname='"+nickusers+ "'");
            if (st.next()){
              idusers=st.getInt(1);
            }
            st=stmt.executeQuery("select id from main where nickname='"+nickblock+"'");
            if (st.next()){
                id_block=st.getInt(1);
            }
            String query = "INSERT INTO block_id(id_users,id_block) values (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idusers);
            ps.setInt(2, id_block);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Boolean ExistsInBlock_id(String nickblock, String nickuser) {
        Boolean rt=false;
        try {

            ResultSet st = stmt.executeQuery("SELECT b.id FROM block_id b"+"\n" +
                                            "INNER JOIN main m on b.id_users=m.id"+"\n" +
                                            "WHERE id_block in (select id from main where nickname='"+nickblock+"'"+ ")\n"+
                                            "AND id_users  in (select id from main where nickname='"+nickuser+"'"+")");
            if (st.next()) {
                rt=true;
            } else rt= false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return rt;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
