package com.example.weblab2;

import java.sql.*;

public class DBFacade {
    public static class DBNotConnectedException extends Exception
    {
        String message;
        public DBNotConnectedException()
        {
            message = "Error: database was not connected";
        }
    }

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/WebLabs";
    private static final String USER = "postgres";
    private static final String PASS = "shvat8902";

    private Connection conn;

    public void ConnectDB()
    {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        if (conn != null)
            System.out.println("You successfully connected to database now");
        else
            System.out.println("Failed to make connection to database");
    }

    public Statement GetStatement() throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ExecuteUpdate(String table, String[] DBcolumns, Object[] data,
                              String whereCondition)
    {
        try {
            StringBuilder query = new StringBuilder("UPDATE " + table + " SET ");
            for(int i =0; i < DBcolumns.length; i++)
            {
                query.append(DBcolumns[i]).append("=");
                if(data[i] instanceof String)
                    query.append("'").append(data[i].toString()).append("'");
                else
                    query.append(data[i].toString());
                if(i != DBcolumns.length - 1)
                    query.append(", ");
                else
                    query.append(" ");
            }
            query.append(whereCondition).append(";");
            GetStatement().execute(query.toString());
        } catch (DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet ExecuteQuery(String query) throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();

        Statement stmt = GetStatement();
        try {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement executeInsertQuery(String table, String[] columns, Object[] values)
    {
        String questionsLine = new String(new char[columns.length]).replace("\0", "?,");
        String insertQuery = "INSERT INTO " + table + "(" +
                String.join(", ", columns) + ") VALUES(" +
                questionsLine.substring(0, questionsLine.length()-1) + ")";
        PreparedStatement query = null;
        try {
            query = executeInsertQuery(insertQuery, values);
            query.execute();
        } catch (DBNotConnectedException | SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public PreparedStatement executeInsertQuery(String sqlQuery, Object[] values)
            throws DBNotConnectedException
    {
        if(conn == null)
            throw new DBNotConnectedException();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery,
                    Statement.RETURN_GENERATED_KEYS);
            for(int i = 1; i <= values.length; i++)
            {
                pstmt.setObject(i, values[i-1]);
            }
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int GetRowCount(ResultSet set)
    {
        int size = 0;
        try {
            set.last();
            size = set.getRow();
            set.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }
        return size;
    }
}
