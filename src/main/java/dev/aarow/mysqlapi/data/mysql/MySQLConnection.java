package dev.aarow.mysqlapi.data.mysql;

import dev.aarow.mysqlapi.data.mysql.other.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class MySQLConnection {

    public abstract String getServer();
    public abstract int getPort();
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String getDatabase();

    public abstract Map<String, List<Data>> getData();

    public abstract Connection getMainConnection();

    public Connection connect(){
        try{

            synchronized (this){
                if(getMainConnection() != null && !getMainConnection().isClosed()){
                    return null;
                }

                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://" + getServer() + ":"
                        + getPort() + "/" + getDatabase(), getUsername(), getPassword());

            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }
}
