package gestionDB;
import java.sql.*;

public class CreationTable {
	
	static private final String login = "jnivoix";
	static private final String mdp   = "12345";
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://sqletud.u-pem.fr/jnivoix_db";
	
	   public static void main(String[] args) {
		   Connection conn = null;
		   Statement stmt = null;
		   
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,login,mdp);
		      
		      System.out.println("Creating table in given database...");
		      stmt = conn.createStatement();
		      
		      String sql = "CREATE TABLE COMPTE " +
		                   " (NOCOMPTE CHAR(4) not null, " + 
		                   " NOM VARCHAR(20), " +
		                   " PRENOM VARCHAR(20), " + 
		                   " SOLDE DECIMAL(10,2) not null , " + 
		                   " PRIMARY KEY ( NOCOMPTE ))";

		      System.out.println("Created table in given database...");
		      String sql2 = "CREATE TABLE OPERATION " +
	                   " (NOCOMPTE CHAR(4) not null, " + 
	                   " DATE DATE not null, " +
	                   " HEURE TIME not null, " + 
	                   " OP CHAR(1) not null, " + 
	                   " VALEUR DECIMAL(10,2) not null, "+
	                   "FOREIGN KEY (NOCOMPTE) REFERENCES COMPTE(NOCOMPTE))";

	      stmt.executeUpdate(sql);
	      stmt.executeUpdate(sql2);
	      System.out.println("Created table in given database...");
		      
		   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
	   }
}
