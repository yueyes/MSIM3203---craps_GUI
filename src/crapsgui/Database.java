package crapsgui;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.JdbcRowSet;

public class Database {
    final String DB_URL ="jdbc:oracle:thin:@10.244.0.245:1521:mobile";
    final String username ="system";
    final String password ="HSMCnew0";
    
    
  public void Connect(){
  try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
  }

    public void Insert(String SQLstatement, String sid, int dice1, int dice2) {
        Connect();
        Connection connection = null;
        PreparedStatement pstatement = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            connection = DriverManager.getConnection(DB_URL,username,password);
            pstatement = connection.prepareStatement(SQLstatement);
            pstatement.setString(1, sid);
            pstatement.setTimestamp(2, timestamp);
            pstatement.setInt(3, dice1);
            pstatement.setInt(4, dice2);
            pstatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                pstatement.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("\"Failed to make connection!\"");
                e.printStackTrace();
            }
        }

    }
    //To check the data whether recorded
    public List showStat(String SQLstatement){
    Connect();
    JdbcRowSet rowset = null;
    List<Object[]> list = new ArrayList<>();
    try{
        rowset = new JdbcRowSetImpl();
        rowset.setUrl(DB_URL);
        rowset.setUsername(username);
        rowset.setPassword(password);
        rowset.setCommand(SQLstatement);
        rowset.execute();
        ResultSetMetaData metaData = rowset.getMetaData();
        int col =metaData.getColumnCount();
        System.out.println(col);
        while(rowset.next()){
            Object[] obj = new Object[col];
        for(int i=1;i<col+1;i++)
                     obj[i-1]=rowset.getObject(i);
        list.add(obj);

        }
        
        
    }catch(SQLException e){
     e.printStackTrace();
    }finally{
    try{
    rowset.close();
    
    }catch(Exception e){
    System.out.println("\"Failed to make connection!\"");
    e.printStackTrace();
    System.exit(1);
    }
    }
            return list;
    }
    
        public int count(String SQLstatement,String sid, int dice){
    Connection connection = null;
        PreparedStatement pstatement = null;
        int count =0;
        try {
            connection = DriverManager.getConnection(DB_URL,username,password);
            pstatement = connection.prepareStatement(SQLstatement);
            pstatement.setString(1,sid);
            pstatement.setInt(2,dice);
            ResultSet rset =pstatement.executeQuery();
            
            ResultSetMetaData metaData = pstatement.getMetaData();
            while(rset.next()){
                count = rset.getInt(1);
            }
                return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstatement.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("\"Failed to make connection!\"");
                e.printStackTrace();
            }
        }
        return count;
    }
    
    
}
