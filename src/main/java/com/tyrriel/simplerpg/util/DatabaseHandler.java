package com.tyrriel.simplerpg.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class DatabaseHandler {

    JavaPlugin plugin;

    public static Connection connection;
    private Statement statement;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;
    private boolean connectionStatus;


    public DatabaseHandler(JavaPlugin plugin) {
        this.plugin = plugin;

        host = "";
        port = 0;
        database = "";
        username = "";
        password = "";

        if(!initialize()) {
            setConnectionStatus(false);
        } else {
            setConnectionStatus(true);
        }
    }

    private boolean initialize() {
        try {
            openConnection();
            statement = connection.createStatement();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useSSL=false", this.username, this.password);
        }
    }

    public boolean findTable(String tableName) {
        ResultSet result;
        try {
            result = statement.executeQuery("SHOW TABLES LIKE '" + tableName + "';");
            if(result.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            plugin.getLogger().info("Error with finding tables like " + tableName);
            e.printStackTrace();
        }
        return false;
    }

    public Statement getStatement() {
        return statement;
    }

    public boolean getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
