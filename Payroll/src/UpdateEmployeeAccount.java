import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateEmployeeAccount extends HttpServlet { 

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
// get variables and initialise
		String update_id = request.getParameter("update_id");
		String NewAdminStatus = request.getParameter("NewAdminStatus");
		String newAddress = request.getParameter("newAddress");
		String NewPay = request.getParameter("NewPay");
		String NewRole = request.getParameter("NewRole");
		String NewBSB = request.getParameter("NewBSB");
		String NewBank = request.getParameter("NewBank");
		String NewBAN = request.getParameter("NewBAN"); 
		String NewSAC = request.getParameter("NewSAC"); 
		String NewSAN = request.getParameter("NewSAN"); 
// connect to database to replace the old values with these new ones, to update employee details
		try { 
			// connect to DB
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
		    PreparedStatement ps = con.prepareStatement("UPDATE payroll_system.employee_info SET Admin = COALESCE(?, Admin ), Address = COALESCE(?, Address ), HourlyRate = COALESCE(?, HourlyRate ), Role = COALESCE(?, Role ), BSB = COALESCE(?, BSB ), BankName = COALESCE(?, BankName ), Accnumber = COALESCE(?, Accnumber ), SuperNumber = COALESCE(?, SuperNumber ), SuperCompany = COALESCE(?, SuperCompany) WHERE employeeID = ?");
		    
		    if(NewAdminStatus.isEmpty()){ 
		    	ps.setNull(1, Types.BOOLEAN);
		    }
		    	else ps.setString(1, NewAdminStatus);
		    
		    if(newAddress.isEmpty()){ 
		    	ps.setNull(2, Types.VARCHAR);
		    }
		    	else ps.setString(2, newAddress    );
		    
		    if(NewPay.isEmpty()){ 
		    	ps.setNull(3, Types.FLOAT);
		    }
		    	else ps.setString(3, NewPay        );
		    
		    if(NewRole.isEmpty()){
		    	ps.setNull(4, Types.VARCHAR);
		    }
		    	else ps.setString(4, NewRole       );
		    
		    if(NewBSB.isEmpty()){
		    	ps.setNull(5, Types.VARCHAR);
		    }
		    	else ps.setString(5, NewBSB        );
		    
		    if(NewBank.isEmpty()){
		    	ps.setNull(6, Types.VARCHAR);
		    }
		    	else ps.setString(6, NewBank       );
		    
		    if(NewBAN.isEmpty()){
		    	ps.setNull(7, Types.VARCHAR); 
		    }
		    	else ps.setString(7, NewBAN        );
		    
		    if(NewSAN.isEmpty()){
		    	ps.setNull(8, Types.VARCHAR);
		    }
		    	else ps.setString(8, NewSAN        );
		    
		    if(NewSAC.isEmpty()){
		    	ps.setNull(9, Types.VARCHAR);
		    }
		    	else ps.setString(9, NewSAC        );
		    
		    if(update_id.isEmpty()){ 
		    	ps.setNull(10, Types.VARCHAR);
		    }
		    else ps.setString(10, update_id);
		    
		    int i = ps.executeUpdate(); 
		    // if successfully updated, then update login details too
		    if(i>0) { 
		    	PreparedStatement pd = con.prepareStatement("UPDATE payroll_system.employee_login SET Admin=? WHERE employeeID=?");
		    	pd.setString(1, NewAdminStatus);
		    	pd.setString(2, update_id);
		    	pd.executeUpdate(); 
		    	out.println("Employee Details Successfully Updated");
	           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

		    }
		    	
		 }catch(Exception e)
        {
         e.printStackTrace();
       	 out.println("Failed to update details");
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