package live.mufin.MufinCore.databases;

import live.mufin.MufinCore.MufinCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

  private MufinCore core;

  private String host;
  private String port;
  private String database;
  private String username;
  private String password;
  private boolean useSSL;

  private Connection connection;

  public MySQL(MufinCore core, String host, String port, String database, String username, String password, boolean useSSL) {
    this.core = core;

    this.host = host;
    this.port = port;
    this.database = database;
    this.username = username;
    this.password = password;
    this.useSSL = useSSL;

    try {
      this.connect();
      core.sendFormattedMessage(core.plugin.getServer().getConsoleSender(), "MySQL Connection &aSUCCESS");
    } catch(ClassNotFoundException | SQLException e) {
      core.sendFormattedMessage(core.plugin.getServer().getConsoleSender(), "MySQL Connection &cFAILED");
    }
  }

  public boolean isConnected() {
    return (connection != null);
  }

  public void connect() throws ClassNotFoundException, SQLException {
    if(!isConnected())
      connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + useSSL, username, password);
  }

  public void disconnect() {
    if(isConnected())
      try {
        connection.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
  }

  public Connection getConnection() {
    return connection;
  }

}
