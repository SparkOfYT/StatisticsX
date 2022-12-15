package de.sparkofyt.statisticsx;

import de.sparkofyt.statisticsx.databases.DatabaseType;
import de.sparkofyt.statisticsx.databases.mongodb.MongoConnector;
import de.sparkofyt.statisticsx.databases.mysql.MySQLApi;
import de.sparkofyt.statisticsx.databases.mysql.MySQLConnector;
import de.sparkofyt.statisticsx.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class StatisticsX extends JavaPlugin {

    /* Variables */
    private static StatisticsX instance;
    private MongoConnector mongoConnector;
    private MySQLConnector mySQLConnector;
    private MySQLApi mySQLApi;
    private DatabaseType databaseType;

    /* Configs */
    private ConfigManager config;
    private ConfigManager mongoConfig;
    private ConfigManager mysqlConfig;

    /* Methods */
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        setupConfigs();
        setupDatabase();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(mySQLConnector != null) mySQLConnector.disconnect();
    }

    private void setupConfigs() {
        /* StatisticX config */
        config = new ConfigManager(this, this.getDataFolder().getAbsolutePath() + "/", "config.yml");
        config.getStringOrSetDefault("Database.Type", "MySQL");
        config.getBooleanOrSetDefault("Database.Collections.autoCreation", true);
        config.getBooleanOrSetDefault("Database.Collections.sendAlreadyExistsMessage", true);

        /* Mongo config */
        mongoConfig = new ConfigManager(this, this.getDataFolder().getAbsolutePath() + "/database/", "mongo.yml");
        mongoConfig.getBooleanOrSetDefault("URI.useURI", false);
        mongoConfig.getStringOrSetDefault("URI.uri", "");
        mongoConfig.getStringOrSetDefault("host", "");
        mongoConfig.getIntegerOrSetDefault("port", 27071);
        mongoConfig.getStringOrSetDefault("username", "");
        mongoConfig.getStringOrSetDefault("password", "");
        mongoConfig.getStringOrSetDefault("cluster", "");

        /* MySQL config */
        mysqlConfig = new ConfigManager(this, this.getDataFolder().getAbsolutePath() + "/database/", "mysql.yml");
        mysqlConfig.getStringOrSetDefault("host", "");
        mysqlConfig.getIntegerOrSetDefault("port", 3306);
        mysqlConfig.getStringOrSetDefault("username", "");
        mysqlConfig.getStringOrSetDefault("password", "");
        mysqlConfig.getStringOrSetDefault("database", "");
    }

    private void setupDatabase() {
        String db = config.getString("Database.Type");

        switch (db) {
            case "MongoDB":
                mongoConnector = new MongoConnector(
                        mongoConfig.getString("host"),
                        mongoConfig.getInteger("port"),
                        mongoConfig.getString("username"),
                        mongoConfig.getString("password"),
                        mongoConfig.getString("cluster"),
                        mongoConfig.getString("URI.uri")
                );
                mongoConnector.connect();
                databaseType = DatabaseType.MONGO_DB;
                break;
            case "MySQL":
                mySQLConnector = new MySQLConnector(
                        mysqlConfig.getString("host"),
                        mysqlConfig.getInteger("port"),
                        mysqlConfig.getString("username"),
                        mysqlConfig.getString("password"),
                        mysqlConfig.getString("database")
                );
                mySQLConnector.connect();
                mySQLApi = new MySQLApi(mySQLApi.getConnection());
                databaseType = DatabaseType.MYSQL;
                break;
            default:
                getLogger().warning("Invalid Database Type! Check Config.");
                break;
        }
    }

    /* Getters */
    public static StatisticsX getInstance() {
        return instance;
    }
    public ConfigManager getStatisticsXConfig() {
        return config;
    }
    public ConfigManager getMongoConfig() {
        return mongoConfig;
    }
    public ConfigManager getMysqlConfig() {
        return mysqlConfig;
    }
    public MongoConnector getMongoConnector() {
        return mongoConnector;
    }
    public MySQLConnector getMySQLConnector() {
        return mySQLConnector;
    }
    public MySQLApi getMySQLApi() {
        return mySQLApi;
    }
    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}
