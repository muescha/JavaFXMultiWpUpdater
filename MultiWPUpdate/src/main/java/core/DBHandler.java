package core;

import javafx.collections.ObservableList;
import main.model.WpInstance;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Manuel Wimmer
 * Date: 29.11.17
 * Desc:
 */

public class DBHandler {

    private static Connection getDBConnection() throws IOException {
        Properties prop = new Properties();
        String DB_DRIVER = null;
        String DB_CONNECTION = null;
        String DB_USER = null;
        String DB_PASSWORD = null;
        Connection conn = null;
        InputStream input;

        input = DBHandler.class.getResourceAsStream("/properties/database.properties");

        try {
            prop.load(input);

            DB_DRIVER = prop.getProperty("driver");
            DB_CONNECTION = prop.getProperty("url");
            DB_USER = prop.getProperty("username");
            DB_PASSWORD = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (DB_DRIVER != null && DB_CONNECTION != null && DB_USER != null && DB_PASSWORD != null) {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            try {
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                return conn;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }

    public static void addWpInstanceEntry(String name, String host, String user, String pw, int port, String dir) throws SQLException, IOException {
        Connection conn = getDBConnection();

        try {
            String query = "INSERT INTO WP_INSTANCE (NAME, HOST, USER, PASSWORD, PORT, BASE_DIR) values (?,?,?,?,?,?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, name);
            preparedStmt.setString (2, host);
            preparedStmt.setString (3, user);
            preparedStmt.setString (4, pw);
            preparedStmt.setInt (5, port);
            preparedStmt.setString (6, dir);

            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static List<WpInstance> loadAllWpInstances() throws SQLException, IOException {
        Connection conn = getDBConnection();
        List<WpInstance> wpInstances = new ArrayList<>();

        try {
            String query = "SELECT ID, NAME, HOST, USER, PASSWORD, PORT, BASE_DIR FROM WP_INSTANCE";

            if (conn != null) {
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        String host = rs.getString("HOST");
                        String user = rs.getString("USER");
                        String password = rs.getString("PASSWORD");
                        int port = rs.getInt("PORT");
                        String baseDir = rs.getString("BASE_DIR");
                        wpInstances.add(new WpInstance(id, name, host, user, password, port, baseDir));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return wpInstances;
    }

    public static void removeWpInstance(ObservableList<WpInstance> wpList) throws IOException, SQLException {
        Connection conn = getDBConnection();
        try {
            for (WpInstance item : wpList) {
                String query = "DELETE FROM WP_INSTANCE WHERE ID = ?";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, item.getId());
                preparedStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void updateInstance(int id, String name, String host, String user, String password, int port, String baseDir) throws SQLException, IOException {
        Connection conn = getDBConnection();

        try {
            String query = "UPDATE WP_INSTANCE SET NAME=?, HOST=?, USER=?, PASSWORD=?, PORT=?, BASE_DIR=? WHERE ID=?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, name);
            preparedStmt.setString (2, host);
            preparedStmt.setString (3, user);
            preparedStmt.setString (4, password);
            preparedStmt.setInt (5, port);
            preparedStmt.setString (6, baseDir);
            preparedStmt.setInt (7, id);

            preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static WpInstance loadInstanceById(int instanceId) throws SQLException, IOException {
        Connection conn = getDBConnection();
        WpInstance instance = null;

        try {
            String query = "SELECT ID, NAME, HOST, USER, PASSWORD, PORT, BASE_DIR FROM WP_INSTANCE WHERE ID=?";

            if (conn != null) {
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt (1, instanceId);
                ResultSet rs = preparedStmt.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        String host = rs.getString("HOST");
                        String user = rs.getString("USER");
                        String password = rs.getString("PASSWORD");
                        int port = rs.getInt("PORT");
                        String baseDir = rs.getString("BASE_DIR");
                        instance = new WpInstance(id, name, host, user, password, port, baseDir);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return instance;
    }
}
