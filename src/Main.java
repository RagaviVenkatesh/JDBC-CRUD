import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
				//Step 1 - Load the driver
				try 
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
				} 
				catch (ClassNotFoundException e) 
				{
					
					e.printStackTrace();
				}
				
				//Step 2 - Establish Connection
				Connection con = null;
				
				try 
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample","root","SYSTEM@2121");
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(con!=null) {
					System.out.println("Connection Established");
				}
				else {
					System.out.println("Connection not Established");
				}
				
				System.out.println("Enter the details to be inserted");
				Scanner sc = new Scanner(System.in);
				String username = sc.nextLine();
				String password = sc.nextLine();
				String fullname = sc.nextLine();
				String email = sc.nextLine();
				
				//Insert a user
				String sql = "INSERT INTO users(username,password,fullname,email)VALUES(?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, fullname);
				ps.setString(4, email);
				System.out.println("_______________________________________________________");
				
				int rows = ps.executeUpdate();
				if(rows>0)
				{
					System.out.println("A new user inserted successfully!");
				}
				
				//SELECT ALL USERS
				System.out.println("_______________________________________________________");
				System.out.println("List of all users");
				String query = "SELECT * FROM users";
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next())
				{
//					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)
//					+" "+rs.getString(4)+" "+rs.getString(5));
					System.out.println(rs.getInt("user_id")+" "+rs.getString("username")+" "+rs.getString("password")
					+" "+rs.getString("fullname")+" "+rs.getString("email"));
					System.out.println("_______________________________________________________");
				}
				
//				//SELECT A PARTICULAR USER
				System.out.println("Select a user with their user name");
				username = sc.nextLine();
				query = "SELECT * FROM users WHERE username=?";
				ps = con.prepareStatement(query);
				ps.setString(1, username);
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					System.out.println(rs.getInt("user_id")+" "+rs.getString("username")+" "+rs.getString("password")
					+" "+rs.getString("fullname")+" "+rs.getString("email"));
					System.out.println("_______________________________________________________");
				}
				
				//UPDATE A USER DETAIL
				System.out.println("Update user email");
				System.out.println("Enter the user id");
				int id = Integer.parseInt(sc.nextLine());
				System.out.println("Enter the new email");
				email = sc.nextLine();
				
				String sql1 = "UPDATE users SET email=? WHERE user_id=?";
				ps = con.prepareStatement(sql1);
				ps.setString(1, email);
				ps.setInt(2, id);
				int result = ps.executeUpdate();
				if(result>0)
					System.out.println("Email updated Successfully!");
				System.out.println("_______________________________________________________");
				
				
				//DELETE A USER
				System.out.println("Enter the username you want to delete");
				username = sc.nextLine();
				String sql2 = "DELETE FROM users WHERE username=?";
				ps = con.prepareStatement(sql2);
				ps.setString(1, username);
				result = ps.executeUpdate();
				if(result>0)
					System.out.println("User Deleted Successfully!");
				System.out.println("_______________________________________________________");
	}

}


//Statement -> static SQL query
//preparedStatement -> parameterized SQL Query

//Statement -> execute() -> general SQL Statement
//executeUpdate() -> INSERT, UPDATE, DELETE
//executeQuery() -> SELECT
