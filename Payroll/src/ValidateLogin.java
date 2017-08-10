import java.sql.*;
public class ValidateLogin
{
	public static boolean user(String employee_id, String password)
	{  // validate employee's login by checking in database. 
		boolean st = false;
		try { 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
			PreparedStatement ps = con.prepareStatement("select * from payroll_system.employee_login where employeeID = ? and pwd = ?");
			ps.setString(1, employee_id);
			ps.setString(2, password);
	        ResultSet rs =ps.executeQuery();
	         st = rs.next();

		 }catch(Exception e)
	      {
	          e.printStackTrace();
	      }
	         return st;                 
	  } 
}

