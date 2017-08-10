import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Expenses extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		//initialise variables
        HttpSession session = request.getSession(false);
        String employee_id = ""; 

                   if(session != null) { 
                       employee_id = (String)session.getAttribute("employeeid"); 
                   }

		int expense_id = Integer.parseInt(request.getParameter("expense_id")); 
		String date = request.getParameter("date"); 
		int expenses = Integer.parseInt(request.getParameter("expense"));
		//connect to database and insert values
		try { 
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
			PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO payroll_system.employee_expenses(employeeID) values (?)");
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO payroll_system.expense_master(expense_id,employeeID, Date,Description,Expense) values(?,?,?,?,?)");
			ps.setString(1, employee_id);
			ps.executeUpdate(); 

			
			ps1.setInt(1, expense_id);
			ps1.setString(2, employee_id);
			ps1.setString(3, date);
			if(expense_id==1){
				ps1.setString(4, "Accommodation"); //values to insert into DB
			}
			if(expense_id==2){
				ps1.setString(4, "Travel");
			}
			if(expense_id==3){
				ps1.setString(4, "Meal Allowances");
			}
			if(expense_id==4){
				ps1.setString(4, "Flat Rate Expenses");
			}
			if(expense_id==5){
				ps1.setString(4, "Other");
			}
			ps1.setFloat(5, expenses);
			
			
			int count = ps1.executeUpdate();
			
			if(count>0){
           	 out.println("Details Successfully Submitted");
           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");
            }
			//in case details not submitted
			else{ 
				out.println("Failed to submit details.");
	           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

			}
			//in case connection with DB cannot be made
		 }catch(Exception e)
        {
         e.printStackTrace();
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
	
			