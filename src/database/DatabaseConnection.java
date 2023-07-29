import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private String dbUser;
    private String dbPass;
    private String dbURL;
    private Connection connection;

    private static DatabaseConnection databaseConnection = null;

    private DatabaseConnection(){

    }

    public Connection getConnection() throws IOException {
        File file = new File("src\\db_configuration.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        this.dbUser = properties.getProperty("db_user");
        this.dbPass = properties.getProperty("db_pass");
        this.dbURL = properties.getProperty("db_url");
        try {
            connection = DriverManager.getConnection(this.dbURL,this.dbUser,this.dbPass);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public static DatabaseConnection getNewInstance(){
        synchronized (DatabaseConnection.class){
            if (databaseConnection == null){
                databaseConnection = new DatabaseConnection();
            }
        }
        return databaseConnection;
    }
}
