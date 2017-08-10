import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ViewPayrollItems extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
             
            try{ 
            	// connect to database to retrieve payroll item details
            		boolean st = false; 
            	 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                 PreparedStatement ps = con.prepareStatement("select ItemCode, ItemName, ItemDescription, Rate from payroll_system.payroll_items");
                 ResultSet rs = ps.executeQuery(); 
                 st = rs.next(); 
                 if(st) { // use html to output payroll item details
                	 out.println("<html>");
                     out.println("<head>");
                     out.println("<style>"); 
                     out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                     out.println("tr:hover {background-color: #e2f4ff;}");
                     out.println("</style>");
                     out.println("<link rel = stylesheet type = text/css href = main.css>");
                     out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                     out.println("<title>View Payroll Items</title>");
                     out.print("</head>");
                     
                     out.println("<body>");
                     	out.println("<ul>");
                     	out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                     	out.println("<li><a href=ViewEmployeeExpenseClaim>View Expense Claims</a></li>");
                     	out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
                     	out.println("<li><a href=changePassAdmin.html>Change Password</a></li>");
                     	out.println("<li><a href=manageEmployee.html >Maintain Employee Information</a></li>");
                     	out.println("<li><a href=TaxInfo.html>Maintain Tax Information</a></li>");
                     	out.println("<li><a href=Payrollitems.html class=active >Maintain Payroll Items</a></li>");
                     	out.println("<li><a href=TimeSheet.html>Maintain Timesheet</a></li>");
                     	out.println("<li><a href=EmployeeExpense.html>Maintain Expenses</a></li>");
                     	out.println("<li><a href=PayrollRun>Run Payroll</a></li>");
                     	out.println("<li><a href=GenerateReports>Generate Reports</a></li>");
                     	out.println("</ul>");
                     	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                     	out.println("</div>");
                     out.println("<div id=container>");
                     out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");

                     out.println("<h1>Viewing Payroll Items</h1>");
                     out.println("<table border =1>");
                     
         
                     out.println("<tr>");
                     out.println("<td><h3>Item Code</h3></td>");
                     out.println("<td><h3>Item Name</td></h3>");
                     out.println("<td><h3>Item Description</h3></td>");
                     out.println("<td><h3>Rate</h3></td>");
                     out.println("</tr>");
                     
                     out.println("<tr>");
                     out.println("<td>"+ rs.getString("ItemCode")+"</td>");
                     out.println("<td>"+ rs.getString("ItemName")+"</td>");
                     out.println("<td>"+ rs.getString("ItemDescription")+"</td>");
                     out.println("<td>"+ rs.getString("Rate")+"</td>");
                     out.println("</tr>");
                     
                     while (rs.next()) { 

                     out.println("<tr>");
                     out.println("<td>"+ rs.getString("ItemCode")+"</td>");
                     out.println("<td>"+ rs.getString("ItemName")+"</td>");
                     out.println("<td>"+ rs.getString("ItemDescription")+"</td>");
                     out.println("<td>"+ rs.getString("Rate")+"</td>");
                     out.println("</tr>");
                     }
                     
                     out.println("</table>");
                     out.println("</div>");
                     out.println("</body>");
                     out.println("</html>");

                 }
                 else { 
                	 out.println("There are currently no Payroll Items"); 
                   	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                 }
                    }catch(Exception e)
                     {
                         e.printStackTrace();
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
            
                
                	
               