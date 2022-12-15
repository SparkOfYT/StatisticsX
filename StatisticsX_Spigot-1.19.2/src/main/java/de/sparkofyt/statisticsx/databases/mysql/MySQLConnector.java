package de.sparkofyt.statisticsx.databases.mysql;

import de.sparkofyt.statisticsx.StatisticsX;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    /* Variables */
    private Connection connection;
    private final String host, username, password, database;
    private final int port;

    /* Constructor */
    public MySQLConnector(@NotNull String host, @NotNull Integer port, @NotNull String username, @NotNull String password, @NotNull String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    /* Methods */
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            StatisticsX.getInstance().getLogger().info("Successfully connected to MySQL Database!");
        } catch (ClassNotFoundException | SQLException exception) {
            StatisticsX.getInstance().getLogger().warning("Error while connecting to MySQL Database!");
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        if(!isConnected()) return;
        try {
            connection.close();
            StatisticsX.getInstance().getLogger().info("Successfully disconnected MySQL Database!");
        } catch (SQLException exception) {
            StatisticsX.getInstance().getLogger().warning("Error while disconnecting MySQL Database!");
            exception.printStackTrace();
        }
    }

    public boolean isConnected() {
        if(connection == null) return false;
        try {
            return !connection.isClosed();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /* Getters & Setters */
    public Connection getConnection() {
        if(isConnected()) return connection;
        return null;
    }
    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getDatabase() {
        return database;
    }
}
