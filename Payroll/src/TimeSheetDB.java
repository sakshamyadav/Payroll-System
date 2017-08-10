import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TimeSheetDB extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
	// get employee id of logged in employee
        HttpSession session = request.getSession(false);
        String employee_id = ""; 

                   if(session != null) { 
                       employee_id = (String)session.getAttribute("employeeid"); 
                   }
// get variables from html page to store into timesheet DB 
		int hours = Integer.parseInt(request.getParameter("HoursWorked")); 
		String date = request.getParameter("date"); 
		int ALeave = Integer.parseInt(request.getParameter("ALeave")); 
		int SLeave = Integer.parseInt(request.getParameter("SLeave"));
		// store details into timesheet DB
		try { 
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
			PreparedStatement ps = con.prepareStatement("INSERT INTO payroll_system.monthly_timesheet(employeeID, Date, Hours, AnnualLeave, SickLeave) values (?,?,?,?,?)");
			PreparedStatement ps1 = con.prepareStatement("UPDATE payroll_system.employee_info SET ALeaveBalance=?, SLeaveBalance=? WHERE employeeID=?"); 
			PreparedStatement ps0 = con.prepareStatement("select employeeID from payroll_system.employee_info where employeeID=?");
			ps.setString(1, employee_id);
			ps.setString(2, date);
			ps.setInt(3, hours);
			ps.setInt(4, ALeave);
			ps.setInt(5, SLeave);
			
			boolean sr = false; 
			ps0.setString(1, employee_id);
			ResultSet rx = ps0.executeQuery(); 
			sr = rx.next(); 
			// select leave balance from table
		if(sr){ 
			
			ps.executeUpdate(); 
			
			boolean st = false; 
			PreparedStatement ps2 = con.prepareStatement("SELECT ALeaveBalance, SLeaveBalance FROM payroll_system.employee_info WHERE employeeID=?"); 
			ps2.setString(1, employee_id);
			ResultSet rd = ps2.executeQuery(); 
			st = rd.next(); 
			int currentALeave = rd.getInt("ALeaveBalance"); 
			int currentSLeave = rd.getInt("SLeaveBalance"); 
			// update leave balance based on how many the employee used when entering timesheet
			int updatedALeave = currentALeave - ALeave; 
			int updatedSLeave = currentSLeave - SLeave; 
			
			ps1.setInt(1, updatedALeave);
			ps1.setInt(2, updatedSLeave);
			ps1.setString(3, employee_id);
			
			ps1.executeUpdate(); 
           	 out.println("Details Successfully Submitted");
           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

		}
		
		else{
	       	 out.println("Employee not found!");
           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

		}
			
		 }catch(Exception e)
        {
         e.printStackTrace();
       	 out.println("Failed to submit details. Time sheet has already been submitted for this employee, for the current payroll period.");
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
