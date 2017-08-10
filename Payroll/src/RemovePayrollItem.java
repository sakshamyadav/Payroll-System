import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RemovePayrollItem extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		
		String code = request.getParameter("code1"); 
//connect with DB to delete payroll items
		try { 
			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
             PreparedStatement ps = con.prepareStatement("DELETE from payroll_system.payroll_items WHERE ItemCode = ?"); 
             ps.setString(1, code);
             
             int count = ps.executeUpdate(); 
             
             if(count>0) { 
            	 out.println("Payroll item successfully deleted");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

             }
             else { 
             	 out.println("Payroll item could not be deleted as it doesn't exist");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

             }

		 }catch(Exception e)
      {
       e.printStackTrace();
     	 out.println("Payroll item could not be deleted as it doesn't exist");
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
	