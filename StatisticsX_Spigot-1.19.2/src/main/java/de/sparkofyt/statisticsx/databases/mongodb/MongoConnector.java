package de.sparkofyt.statisticsx.databases.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.sparkofyt.statisticsx.StatisticsX;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MongoConnector {

    /* Variables */
    private MongoClient client;
    private final String host, username, password, cluster, URI;
    private final int port;

    /* Constructor */
    public MongoConnector(@NotNull String host, @NotNull Integer port, @NotNull String username, @NotNull String password, @NotNull String cluster, String URI) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.cluster = cluster;
        this.URI = URI;
    }

    /* Methods */
    public void connect() {
        if(StatisticsX.getInstance().getMongoConfig().getBoolean("URI.useURI")) {
            String uri = StatisticsX.getInstance().getMongoConfig().getString("URI.uri");

            ConnectionString connectionString = new ConnectionString(uri);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            client = MongoClients.create(settings);
        } else {
            MongoCredential credential = MongoCredential.createCredential(username, cluster, password.toCharArray());
            List<ServerAddress> serverAddresses = Collections.singletonList(new ServerAddress(host, port));

            client = MongoClients.create(MongoClientSettings.builder()
                    .credential(credential)
                    .applyToClusterSettings(builder -> builder.hosts(serverAddresses).build())
                    .build()
            );
        }
        testConnection();
    }

    public void testConnection() {
        MongoDatabase database = client.getDatabase("admin");
        Bson command = new BsonDocument("ping", new BsonInt64(1));

        try {
            database.runCommand(command);
            StatisticsX.getInstance().getLogger().info("Successfully connected to MongoDB Database!");
        } catch (MongoException mongoException) {
            client.close();
            StatisticsX.getInstance().getLogger().warning("Error while connecting MongoDB Database!");
        }
    }

    /* Getters & Setters */
    public MongoClient getClient() {
        return client;
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
    public String getCluster() {
        return cluster;
    }
    public String getURI() {
        return URI;
    }
}
