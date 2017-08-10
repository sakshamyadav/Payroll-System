import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddTax extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//initialise variables
		String bracket = request.getParameter("bracket"); 
		String from = request.getParameter("FromSalary");
		String to = request.getParameter("ToSalary"); 
		String rate = request.getParameter("rate"); 
		//connect/insert into DB
		try { 
			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
             PreparedStatement ps = con.prepareStatement("INSERT INTO payroll_system.tax_info(TaxBracket, FromSalary, ToSalary, TaxPercentage) values(?,?,?,?)"); 
             ps.setString(1, bracket);
             ps.setString(2, from);
             ps.setString(3, to);
             ps.setString(4, rate);
             
             int count = ps.executeUpdate(); 
             
             if(count>0) { 
            	 out.println("Tax Bracket successfully added");
               	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

             }
		//exception in case connection cannot be made

		 }catch(Exception e)
       {
        e.printStackTrace();
      	 out.println("Failed to add Tax Bracket. Please ensure Tax Bracket doesn't already exist.");
       	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

       }
     out.close();
}
//override method
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