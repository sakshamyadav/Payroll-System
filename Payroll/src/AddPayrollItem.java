import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddPayrollItem extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//initialise variables
		String code = request.getParameter("code"); 
		String name = request.getParameter("name");
		String description = request.getParameter("description"); 
		String rate = request.getParameter("rate"); 
		//connect to DB and insert values to DB
		try { 
			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
             PreparedStatement ps = con.prepareStatement("INSERT INTO payroll_system.payroll_items(ItemCode, ItemName, ItemDescription, Rate) values(?,?,?,?)"); 
             ps.setString(1, code);
             ps.setString(2, name);
             ps.setString(3, description);
             ps.setString(4, rate);
             
             int count = ps.executeUpdate(); 
             
             if(count>0) { 
            	 out.println("Payroll item successfully added");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

             }
		
//exception in case values are not updated
		 }catch(Exception e)
       {
        e.printStackTrace();
      	 out.println("Failed to add payroll item");
       	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

       }
     out.close(); //close DB
}
//Override put in place to cater for both post and get methods
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
	