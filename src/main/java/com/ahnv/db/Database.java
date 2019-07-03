package com.ahnv.db;
import com.ahnv.entities.Password;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Abhinav.
 */

public class Database {
    private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/project";
    private final String ID = "java";
    private final String PASS = "java";
    private Connection conn;

    public Database() {
        try {
            Class.forName(DRIVER_NAME);
            Properties properties = new Properties();
            properties.setProperty("user", ID);
            properties.setProperty("password", PASS);
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            this.conn = DriverManager.getConnection(DB_URL,properties);
        } catch (Exception e){ throw new RuntimeException(e); }
    }

    public List<Password> findAll(){
        PreparedStatement stmt = null;
        List<Password> list = new ArrayList<Password>();
        try {
            stmt = this.conn.prepareStatement("SELECT `id`,`name`,`url`,`username`, `password`,`notes` FROM `data` ORDER BY `id`");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Password password = new Password();
                password.setId(rs.getInt("id"));
                password.setName(rs.getString("name"));
                password.setUsername(rs.getString("username"));
                password.setPassword(rs.getString("password"));
                password.setUrl(rs.getString("url"));
                password.setNotes(rs.getString("notes"));
                list.add(password);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Password findOne(int id){
        PreparedStatement stmt = null;
        try{
            stmt = this.conn.prepareStatement("SELECT `id`,`name`,`url`,`username`, `password`,`notes` FROM `data` WHERE `id` = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Password password = new Password();
                password.setId(rs.getInt("id"));
                password.setName(rs.getString("name"));
                password.setUsername(rs.getString("username"));
                password.setPassword(rs.getString("password"));
                password.setUrl(rs.getString("url"));
                password.setNotes(rs.getString("notes"));
                return password;
            }else return null;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(Password p){
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("INSERT INTO `data` (`name`, `url`, `username`, `password`, `notes`) VALUES ( ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,p.getName());
            stmt.setString(2,p.getUrl());
            stmt.setString(3,p.getUsername());
            stmt.setString(4,p.getPassword());
            stmt.setString(5,p.getNotes());
            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){ p.setId(rs.getInt(1)); }
            return result;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int update(Password p){
        PreparedStatement stmt = null;
        try {
            stmt = this.conn.prepareStatement("UPDATE `data` SET `name` = ?, `url` = ?, `username` = ?, `password` = ? , `notes` = ? WHERE `data`.`id` = ?");
            stmt.setString(1,p.getName());
            stmt.setString(2,p.getUrl());
            stmt.setString(3,p.getUsername());
            stmt.setString(4,p.getPassword());
            stmt.setString(5,p.getNotes());
            stmt.setInt(6,p.getId());
            return stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int remove(Password p){
        PreparedStatement stmt = null;
        try{
            stmt = this.conn.prepareStatement("DELETE FROM `data` WHERE `data`.`id` = ?");
            stmt.setInt(1,p.getId());
            return stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static void close(Connection con) {
        if (con != null){
            try{ con.close(); }
            catch (SQLException e){ throw new RuntimeException(e); }
        }
    }
}
