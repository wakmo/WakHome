package com.mkyong.demo;

import java.sql.*;

/**
 * User: wakkir
 * Date: 09/03/14
 * Time: 22:33
 */
public class RunDB
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        Connection conn = null;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String connUrl = "jdbc:sqlserver://localhost:1433;user=hello;password=hello123;database=2pc";
            conn = DriverManager.getConnection(connUrl);

            //Class.forName("com.mysql.jdbc.Driver");

            //conn = DriverManager.getConnection("jdbc:mysql://85.214.52.244:3306/wordpress_4?user=wakkir&password=Wak@Test1234");

            System.out.println("db connected....." + connUrl);
            Statement sta = conn.createStatement();
            String Sql = "select * from MyTestTable";
            ResultSet rs = sta.executeQuery(Sql);
            while (rs.next())
            {
                System.out.println(rs.getString("name"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e)
                {

                }
            }
        }
    }
}
