import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RemoveTax extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		
//delete all fields in the tax table to update with new information given by government
		try { 
			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
             Statement truncate = con.createStatement();
             truncate.executeUpdate("TRUNCATE payroll_system.tax_info");
             
             
            	 out.println("Tax table successfully removed");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");
             
       

		 }catch(Exception e)
      {
       e.printStackTrace();
     	 out.println("Tax Bracket could not be deleted as it doesn't exist");
       	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");
      }
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
