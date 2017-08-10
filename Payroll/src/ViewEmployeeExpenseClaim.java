import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ViewEmployeeExpenseClaim extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            HttpSession session = request.getSession(false);
            String employee_id = ""; 
// get employee id of logged in employee
                       if(session != null) { 
                           employee_id = (String)session.getAttribute("employeeid"); 
                       }            
            boolean st = false; 
            try{ // connect to database to get information about employee's expense claims
            	 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                 PreparedStatement ps = con.prepareStatement("select employeeID,  Date, Description, Expense from payroll_system.expense_master where employeeID = ?");
                 ps.setString(1, employee_id);
                 ResultSet rs = ps.executeQuery(); 
                 st = rs.next(); 
                 // select the sum of all expenses for this particular employee
                 PreparedStatement ps1 = con.prepareStatement("Select Sum(Expense) as sum from payroll_system.expense_master WHERE employeeID = ?"); 
                 ps1.setString(1, employee_id);
                 ResultSet rt = ps1.executeQuery();
                 


                                 

                 if(st && rt.next()) { 

                    // output html to display the expense claims made by this employee
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<style>"); 
                    out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                    out.println("tr:hover {background-color: #e2f4ff;}");
                    out.println("</style>");
                    out.println("<link rel = stylesheet type = text/css href = main.css>");
                    out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                    out.println("<title>View Expense Claims</title>");
                    out.print("</head>");
                    
                    out.println("<body>");
                    	out.println("<ul>");
                    	out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                    	out.println("<li><a class=active>View Expense Claims</a></li>");
                    	out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
                    	out.println("<li><a href=changePassAdmin.html>Change Password</a></li>");
                    	out.println("<li><a href=manageEmployee.html >Maintain Employee Information</a></li>");
                    	out.println("<li><a href=TaxInfo.html>Maintain Tax Information</a></li>");
                    	out.println("<li><a href=Payrollitems.html>Maintain Payroll Items</a></li>");
                    	out.println("<li><a href=TimeSheet.html>Maintain Timesheet</a></li>");
                    	out.println("<li><a href=EmployeeExpense.html>Maintain Expenses</a></li>");
                    	out.println("<li><a href=PayrollRun>Run Payroll</a></li>");
                    	out.println("<li><a href=GenerateReports>Generate Reports</a></li>");
                    	out.println("</ul>");
                    	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                    	out.println("</div>");
                    out.println("<div id=container>");
                    out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");

                    out.println("<h2>Your Employee ID is: " +rs.getString("employeeID")+ "</h2>");
                    out.println("<table border =1>");
                    
                    out.println("<table> <tr> <th>Date</th> <th>Description</th> <th>Expense Incurred</th> </tr>");
                    out.println("<tr> <td>"+rs.getDate("Date")+"</td> <td>"+rs.getString("Description")+"</td> <td>"+"$"+""+rs.getFloat("Expense")+"</td> </tr>");
                    
                    while (rs.next()) { 
                        out.println("<tr> <td>"+rs.getDate("Date")+"</td> <td>"+rs.getString("Description")+"</td> <td>"+"$"+""+rs.getFloat("Expense")+"</td> </tr>");
                    }
                    out.println("<tr> <td>"+"TOTAL"+"</td> <td>"+"----------------------------"+"</td> <td>"+"$"+""+rt.getFloat("sum")+"</td> </tr>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
  


                 }
                 
                 else{ 
                	 out.println("You currently have no expense claims"); 
                   	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                   	 
                 
                 }
            }catch(Exception e)
            {
                e.printStackTrace();
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
            