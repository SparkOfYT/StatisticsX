package de.sparkofyt.statisticsx.databases.mysql;

import de.sparkofyt.statisticsx.StatisticsX;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLApi {

    /* Variables */
    private final Connection connection;

    /* Constructor */
    public MySQLApi(@NotNull Connection connection) {
        this.connection = connection;
    }

    /* Methods */
    public boolean tableExists(String table) throws SQLException {
        boolean tableExists = false;
        try(ResultSet rs = connection.getMetaData().getTables(null, null, table, null)) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if(tableName != null && tableName.equals(table)) {
                    tableExists = true;
                    break;
                }
            }
        }
        return tableExists;
    }
    public void createTable(String table, String tableValues) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + table + "("+tableValues+")";

            stmt.executeUpdate(sql);
            StatisticsX.getInstance().getLogger().info("Created table in given database...");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void dropTable(String table) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "DROP TABLE " + table;

            stmt.executeUpdate(sql);
            StatisticsX.getInstance().getLogger().info("Dropped table in given database...");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /* Getters & Setters */
    public Connection getConnection() {
        return connection;
    }
}
