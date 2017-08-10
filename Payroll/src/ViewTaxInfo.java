import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ViewTaxInfo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
             
            try{ 
            	// connect to database to receive tax information
            		boolean st = false; 
            	 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                 PreparedStatement ps = con.prepareStatement("select TaxBracket, FromSalary, ToSalary, TaxPercentage from payroll_system.tax_info");
                 ResultSet rs = ps.executeQuery(); 
                 st = rs.next(); 
                 if(st) { 
                	 // use html to output tax information
                	 out.println("<html>");
                     out.println("<head>");
                     out.println("<style>"); 
                     out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                     out.println("tr:hover {background-color: #e2f4ff;}");
                     out.println("</style>");
                     out.println("<link rel = stylesheet type = text/css href = main.css>");
                     out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                     out.println("<title>View Tax Information</title>");
                     out.print("</head>");
                     
                     out.println("<body>");
                     	out.println("<ul>");
                     	out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                     	out.println("<li><a href=ViewEmployeeExpenseClaim>View Expense Claims</a></li>");
                     	out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
                     	out.println("<li><a href=changePassAdmin.html>Change Password</a></li>");
                     	out.println("<li><a href=manageEmployee.html >Maintain Employee Information</a></li>");
                     	out.println("<li><a href=TaxInfo.html class=active>Maintain Tax Information</a></li>");
                     	out.println("<li><a href=Payrollitems.html >Maintain Payroll Items</a></li>");
                     	out.println("<li><a href=TimeSheet.html>Maintain Timesheet</a></li>");
                     	out.println("<li><a href=EmployeeExpense.html>Maintain Expenses</a></li>");
                     	out.println("<li><a href=PayrollRun>Run Payroll</a></li>");
                     	out.println("<li><a href=GenerateReports>Generate Reports</a></li>");
                     	out.println("</ul>");
                     	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                     	out.println("</div>");
                     out.println("<div id=container>");
                     out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");

                     out.println("<h1>Viewing Tax Information</h1>");
                     out.println("<table border =1>");
                     
         
                     out.println("<tr>");
                     out.println("<td><h3>Tax Bracket</h3></td>");
                     out.println("<td><h3>From Salary</h3></td>");
                     out.println("<td><h3>To Salary</td></h3>");
                     out.println("<td><h3>Tax Rate</h3></td>");
                     out.println("</tr>");
                     
                     out.println("<tr>");
                     out.println("<td>"+ rs.getString("TaxBracket")+"</td>");
                     out.println("<td>"+"$"+ rs.getString("FromSalary")+"</td>");
                     out.println("<td>"+"$"+ rs.getString("ToSalary")+"</td>");
                     out.println("<td>"+rs.getString("TaxPercentage")+"%"+"</td>");
                     out.println("</tr>");
                     
                     while (rs.next()) { 

                         out.println("<tr>");
                         out.println("<td>"+ rs.getString("TaxBracket")+"</td>");
                         out.println("<td>"+"$"+ rs.getString("FromSalary")+"</td>");
                         out.println("<td>"+"$"+ rs.getString("ToSalary")+"</td>");
                         out.println("<td>"+rs.getString("TaxPercentage")+"%"+"</td>");
                         out.println("</tr>");
                     }
                     
                     out.println("</table>");
                     out.println("</div>");
                     out.println("</body>");
                     out.println("</html>");

                 }
                 else { 
                	 out.println("There is currently no taxation information"); 
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
            
                
                	
               