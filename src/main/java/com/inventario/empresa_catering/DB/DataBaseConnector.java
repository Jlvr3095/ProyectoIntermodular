package com.inventario.empresa_catering.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private static DataBaseConnector instance = null;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/empresa_eventos?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Jlvr2024";
    private Connection connection;


    public DataBaseConnector(){


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            System.out.println("Conexion exitosa");
        }catch (SQLException e){
            System.out.println("Fallo");
            System.out.println(e.getStackTrace());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataBaseConnector GetInstance(){
        if(instance == null){
            instance = new DataBaseConnector();

        }

        return instance;
    }

    public Connection GetConnection(){
        return connection;
    }
}

