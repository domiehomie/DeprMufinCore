package live.mufin.MufinCore.databases;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import live.mufin.MufinCore.MufinCore;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Collections;

public class MongoDB {

  private MongoClient client;
  private MongoDatabase mongoDatabase;
  private MongoCollection<Document> serverCollection;

  private final String username;
  private final String database;
  private final String password;
  private final String host;
  private final int port;
  private final MufinCore core;

  public MongoDatabase getMongoDatabase() {
    return mongoDatabase;
  }

  public MongoCollection<Document> getServerCollection() {
    return serverCollection;
  }

  public MongoClient getClient() {
    return client;
  }

  public MongoDB(MufinCore core, String username, String database, String password, String host, int port) {
    this.username = username;
    this.database = database;
    this.password = password;
    this.host = host;
    this.port = port;
    this.core = core;
  }

  public void connect() {
    MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
    client = new MongoClient(new ServerAddress(host, port), Collections.singletonList(mongoCredential));
    mongoDatabase = client.getDatabase(database);
    serverCollection = mongoDatabase.getCollection("Server");
    core.sendFormattedMessage(core.plugin.getServer().getConsoleSender(), "MongoDB connection &aSUCCESS");
  }
}
