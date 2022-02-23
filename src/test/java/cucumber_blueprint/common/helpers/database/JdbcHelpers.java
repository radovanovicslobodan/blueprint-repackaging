package cucumber_blueprint.common.helpers.database;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;

public class JdbcHelpers {
    // JDBC driver name and database URL
    public  String jdbcDriver = "org.postgresql.Driver";

    public  String dbBaseUrl = "jdbc:postgresql://dbUrl";
    public  String dbName;
    public  String dbUrl;

    public JdbcHelpers(String dbName) {
        this.dbName = dbName;
        dbUrl = dbBaseUrl + dbName;
    }

    //  Database credentials
//    public static String dbUser = null;
//    public static String dbPass = null;
    public  String dbUser = "username";
    public  String dbPass = "password";




    /**
     * Method will return string desired value from query
     *
     * @param sqlStatement desired query
     * @param columnName   column name that you want to extract value
     * @return String
     * @author dino.rac
     */
    public  String getStringValueFromDb(String sqlStatement, String columnName) {
        Connection conn = null;
        Statement stmt = null;
        String value = null;

        try {
            //Register JDBC driver
            Class.forName(jdbcDriver).newInstance();

            //Open a connection
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            //Execute a query
            stmt = conn.createStatement();

            String sql = sqlStatement;
            ResultSet rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                value = rs.getString(columnName);
                //Display values
                System.out.print("\nID: " + value);
            }
            rs.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Method will return Integer desired value from query
     *
     * @param sqlStatement desired query
     * @param columnName   column name that you want to extract value
     * @return Integer
     * @author dino.rac
     */
    public  Integer getIntValueFromDb(String sqlStatement, String columnName) {
        Connection conn = null;
        Statement stmt = null;
        Integer value = null;

        try {
            //Register JDBC driver
            Class.forName(jdbcDriver);

            //Open a connection
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            //Execute a query
            stmt = conn.createStatement();

            String sql = sqlStatement;
            ResultSet rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                value = rs.getInt(columnName);
                //Display values
                System.out.print("\nID: " + value);
            }
            rs.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Method will return true if sql statement is executed correctly
     *
     * @param sqlStatement desired query
     * @return boolean
     * @author dino.rac
     */
    public  Boolean executeUpdate(String sqlStatement) {
        Connection conn = null;
        Statement stmt = null;
        Boolean value = null;

        try {
            //Register JDBC driver
            Class.forName(jdbcDriver).newInstance();

            //Open a connection
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            //Execute a query
            stmt = conn.createStatement();

            String sql = sqlStatement;
            stmt.executeUpdate(sql);

            value = true;

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return value;
    }


    /**
     * Method will return Integer desired value from query
     *
     * @param sqlStatement desired query
     * @param columnName   column name that you want to extract value
     * @return Integer
     * @author dino.rac
     */
    @SneakyThrows
    public ArrayList<String> getArrayStringValuesFromDb(String sqlStatement, String columnName) {
        Connection conn = null;
        Statement stmt = null;
        String value = null;
        ArrayList<String> list = new ArrayList<>();

        try {
            //Register JDBC driver
            Class.forName(jdbcDriver).newInstance();

            //Open a connection
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            //Execute a query
            stmt = conn.createStatement();

            String sql = sqlStatement;
            ResultSet rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                value = rs.getString(columnName);
                list.add(value);
                //Display values
                System.out.print("\nID: " + value);
            }
            rs.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }


}
