import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class NewEmployeeAccount extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//initialise variables
		String employee_id = request.getParameter("employee_id"); 
		String firstName = request.getParameter("firstName"); 
		String lastName = request.getParameter("lastName"); 
		String admin = request.getParameter("admin"); 
		String password = request.getParameter("password"); 
		String DOB = request.getParameter("DOB"); 
		String address = request.getParameter("address"); 
		String email = request.getParameter("email"); 
		float HPR = Float.parseFloat(request.getParameter("HPR")); 
		String gender = request.getParameter("gender"); 
		int ALB = Integer.parseInt(request.getParameter("ALB")); 
		int SLB = Integer.parseInt(request.getParameter("SLB")); 
		String Role = request.getParameter("Role"); 
		String BSB = request.getParameter("BSB"); 
		String BankName = request.getParameter("BankName"); 
		String BAN = request.getParameter("BAN"); 
		String SAC = request.getParameter("SAC"); 
		String SAN = request.getParameter("SAN"); 
		//connect to DB to insert values
		try{ 
			
			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
             PreparedStatement ps = con.prepareStatement("INSERT INTO payroll_system.employee_info(employeeID, FirstName, LastName, Admin,DOB,Address,Email,HourlyRate,Gender,ALeaveBalance,SLeaveBalance,ActiveStatus,Role,BSB,BankName,AccNumber,SuperNumber,SuperCompany) values(?,?,?,?,?,?,?,?,?,?,?,1,?,?,?,?,?,?)"); 
             ps.setString(1, employee_id);
             ps.setString(2, firstName);
             ps.setString(3, lastName);
             ps.setString(4, admin);
             ps.setString(5, DOB);
             ps.setString(6, address);
             ps.setString(7, email);
             ps.setFloat(8, HPR);
             ps.setString(9, gender);
             ps.setInt(10, ALB);
             ps.setInt(11, SLB);
             ps.setString(12, Role);
             ps.setString(13, BSB);
             ps.setString(14, BankName);
             ps.setString(15, BAN);
             ps.setString(16, SAC);
             ps.setString(17, SAN);

             int count = ps.executeUpdate(); 
             //if successful, insert into login database so that employee can log in 
             if(count>0) { 
            	 PreparedStatement pd = con.prepareStatement("INSERT INTO payroll_system.employee_login(employeeID,FirstName,LastName,pwd,Admin) values(?,?,?,?,?)"); 
            	 pd.setString(1, employee_id);
            	 pd.setString(2, firstName);
            	 pd.setString(3, lastName);
            	 pd.setString(4, password);
            	 pd.setString(5, admin);
            	 pd.executeUpdate(); 
            	 out.println("Employee Account Successfully Created");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

               	 
             }
 
             //if db connection unsuccessful
		 }catch(Exception e)
        {
         e.printStackTrace();
       	 out.println("Failed to create account");
       	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

        }
      out.close();
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               processRequest(request, response);
          }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               processRequest(request, response);
          }

    }
	