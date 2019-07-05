package util;

import java.sql.*;

public class GetConnection {
	public Connection getConn(){
		Connection conn=null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					"root", "mysql123456");
		}catch(ClassNotFoundException e){
			System.err.println(e.toString());
		}catch(SQLException ex){
			System.err.println(ex.toString());
		}
		return conn;
	}
}
