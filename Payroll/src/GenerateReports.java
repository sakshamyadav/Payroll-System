import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class GenerateReports extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            //connect to DB to retrieve values 
            try{ 
            	
            		boolean st = false; 
            	 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
  
                 PreparedStatement ps = con.prepareStatement("select employeeID, paydate, role, basic, superannuation, expense_claims, income_tax, medicare, run, hours_worked from payroll_system.payslip");
                 ResultSet rs = ps.executeQuery(); 
                 st = rs.next(); 
                 if(st) { //if values found in DB, output html code to display information
                	 
                	 out.println("<html>");
                     out.println("<head>");
                     out.println("<style>"); 
                     out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                     out.println("tr:hover {background-color: #e2f4ff;}");
                     out.println("</style>");
                     out.println("<link rel = stylesheet type = text/css href = main.css>");
                     out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                     out.println("<title>Generate Reports</title>");
                     out.print("</head>");
                     
                     out.println("<body>");
                     	out.println("<ul>");
                     	out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                     	out.println("<li><a href=ViewEmployeeExpenseClaim>View Expense Claims</a></li>");
                     	out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
                     	out.println("<li><a href=changePassAdmin.html>Change Password</a></li>");
                     	out.println("<li><a href=manageEmployee.html >Maintain Employee Information</a></li>");
                     	out.println("<li><a href=TaxInfo.html>Maintain Tax Information</a></li>");
                     	out.println("<li><a href=Payrollitems.html >Maintain Payroll Items</a></li>");
                     	out.println("<li><a href=TimeSheet.html>Maintain Timesheet</a></li>");
                     	out.println("<li><a href=EmployeeExpense.html>Maintain Expenses</a></li>");
                     	out.println("<li><a href=PayrollRun>Run Payroll</a></li>");
                     	out.println("<li><a class=active>Generate Reports</a></li>");
                     	out.println("</ul>");
                     	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                     	out.println("</div>");
                     out.println("<div id=container>");
                     out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");

                     out.println("<h1>Payroll Summary</h1>");
                     out.println("<table border =1>");
                     	
                     out.println("<table> <tr> <th>Employee ID</th> <th>Role</th> <th>Base Salary</th> <th>Superannuation</th><th>Expense Claims</th><th>Income Tax Paid</th><th>Medicare</th><th>Hours Worked</th> </tr>");
                     out.println("<tr> <td>"+rs.getString("employeeID")+"</td> <td>"+rs.getString("role")+"</td> <td>"+rs.getFloat("basic")+"</td> <td>"+rs.getFloat("superannuation")+"</td> <td>"+rs.getFloat("expense_claims")+"</td><td>"+rs.getFloat("income_tax")+"</td> <td>"+rs.getFloat("medicare")+"</td> <td>"+rs.getFloat("hours_worked")+"</td>  </tr>");
                     
                     while(rs.next()){
                          out.println("<tr> <td>"+rs.getString("employeeID")+"</td> <td>"+rs.getString("role")+"</td> <td>"+rs.getFloat("basic")+"</td> <td>"+rs.getFloat("superannuation")+"</td> <td>"+rs.getFloat("expense_claims")+"</td><td>"+rs.getFloat("income_tax")+"</td> <td>"+rs.getFloat("medicare")+"</td> <td>"+rs.getFloat("hours_worked")+"</td>  </tr>");
                          
                     }
         
                    
//if  there is no data in DB
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
            
