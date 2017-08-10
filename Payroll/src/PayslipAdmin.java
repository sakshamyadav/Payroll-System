import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PayslipAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            //initialise employee id of currently logged in employee 
            HttpSession session = request.getSession(false);
            String employee_id = ""; 

                       if(session != null) { 
                           employee_id = (String)session.getAttribute("employeeid"); 
                       }     
                       boolean sm = false;
                       boolean st = false;
                       boolean sx = false;
                       try{  
                      	 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                           PreparedStatement ps0 = con.prepareStatement("select run from payroll_system.payslip");
                           ResultSet rt = ps0.executeQuery();
                           sx = rt.next();
                           if(sx){ //if entries found in payslip table, select information from two other tables
                        	   boolean run = rt.getBoolean("run");
                        	   if(run){
                        		   
                        		   PreparedStatement ps = con.prepareStatement("select FirstName,Admin, LastName, Address,ALeaveBalance, SLeaveBalance, Role, BankName,AccNumber,SuperNumber,SuperCompany, HourlyRate from payroll_system.employee_info where employeeID = ?");
                                   ps.setString(1, employee_id);
                                   ResultSet rs = ps.executeQuery(); 
                                   st = rs.next();
                                   
                                   PreparedStatement ps1 = con.prepareStatement("select paydate, basic, superannuation, expense_claims, income_tax, medicare, hours_worked, run from payroll_system.payslip where employeeID = ?");
                                   ps1.setString(1, employee_id);
                                   ResultSet rx = ps1.executeQuery();
                                   sm = rx.next();
                                   
                                   //if the values above are retrieved, output html to display employee's payslip
                                   if(st && sm){
                                	   
                                	   boolean adminTrue = rs.getBoolean("Admin"); 
                                       
                                       out.println("<html>");
                                       out.println("<head>");
                                       out.println("<style>"); 
                                       out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                                       out.println("tr:hover {background-color: #e2f4ff;}");
                                       out.println("</style>");
                                       out.println("<link rel = stylesheet type = text/css href = main.css>");
                                       out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                                       out.println("<title>Payslip</title>");
                                       out.print("</head>");
                                       
                                       out.println("<body>");
                                       
                                       if(adminTrue){ 
                                       	out.println("<ul>");
                                       	out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                                       	out.println("<li><a href=ViewEmployeeExpenseClaim>View Expense Claims</a></li>");
                                       	out.println("<li><a class=active>View Payslips</a></li>");
                                       	out.println("<li><a href=changePassAdmin.html>Change Password</a></li>");
                                       	out.println("<li><a href=manageEmployee.html>Maintain Employee Information</a></li>");
                                       	out.println("<li><a href=TaxInfo.html>Maintain Tax Information</a></li>");
                                       	out.println("<li><a href=Payrollitems.html>Maintain Payroll Items</a></li>");
                                       	out.println("<li><a href=TimeSheet.html>Maintain Timesheet</a></li>");
                                       	out.println("<li><a href=EmployeeExpense.html>Maintain Expenses</a></li>");
                                       	out.println("<li><a href=PayrollRun>Run Payroll</a></li>");
                                       	out.println("<li><a href=GenerateReports>Generate Reports</a></li>");
                                       	out.println("</ul>");
                                       	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                                       	out.println("</div>");
                                       }
                                       else if(!adminTrue){ 
                                       	out.println("<ul>");
                                           out.println("<li><a href=PersonalInfoOutput>View Personal Information</a></li>");
                                           out.println("<li><a href=ViewNonAdminExpenseClaim>View Expense Claims</a></li>");
                                           out.println("<li><a class=active>View Payslips</a></li>");
                                           out.println("<li><a href=NonAdminChangePass.html>Change Password</a></li>");
                                       	out.println("<li><a href=NonAdminTimeSheet.html>Maintain Timesheet</a></li>");
                                       	out.println("<li><a href=NonAdminExpenses.html>Maintain Expenses</a></li>");
                                           out.println("</ul>");
                                       	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                                       	out.println("</div>");
                                       	
                                       }
                                       
                                       out.println("<div id=container>");
                                       out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");

                                       out.println("<h1>Payslip</h1>");
                                       out.println("<table border =1>");
                                       
                                       out.println("<tr>");
                                       out.println("<td>Employee ID</td>");
                                       out.println("<td>"+employee_id+ "</td>");
                                       out.println("</tr>");
                                       
                                       out.println("<tr>");
                                       out.println("<td>Name</td>");
                                       out.println("<td>"+ rs.getString("FirstName") + " " + rs.getString("LastName") + "</td>");
                                       out.println("</tr>");
                                       
                                       out.println("<tr>");
                                       out.println("<td>Residential Address</td>");
                                       out.println("<td>"+ rs.getString("Address")+ "</td>");
                                       out.println("</tr>");
                                       
                                       out.println("<tr>");
                                       out.println("<td>Role</td>");
                                       out.println("<td>"+ rs.getString("Role")+ "</td>");
                                       out.println("</tr>");
                                       
                                       out.println("<tr>");
                                       out.println("<td>Pay Date</td>");
                                       out.println("<td>"+ rx.getString("paydate")+ "</td>");
                                       out.println("</tr>");
                                                                              
                                       out.println("<table> <tr> <th>Earnings</th> <th>Hourly Pay Rate</th> <th>Hours Worked</th> <th>Amount</th> </tr>");
                                       out.println("<tr> <td>"+"Basic Income"+"</td> <td>"+"$"+rs.getFloat("HourlyRate")+"</td> <td>"+""+rx.getFloat("hours_worked")+"</td> <td>"+"$"+rx.getFloat("basic")+"</td>" +"</tr>");
                                       out.println("<tr> <td>"+"Expense Claims"+"</td> <td>"+""+"</td> <td> <td>"+"$"+rx.getFloat("expense_claims")+"</td>" +"</tr>");
                                       out.println("<tr> <td>"+"Total Earnings"+"</td> <td>"+""+"</td> <td> <td>"+"$"+(rx.getFloat("expense_claims")+rx.getFloat("basic"))+"</td>" +"</tr>");

                                       out.println("<table> <tr> <th>Deductions/Tax</th> <th>Account Name</th> <th>Account Number</th> <th>Amount</th> </tr>");
                                       out.println("<tr> <td>"+"Superannuation"+"</td> <td>"+rs.getString("SuperCompany")+"</td> <td>"+""+rs.getString("SuperNumber")+"</td> <td>"+"$"+rx.getFloat("superannuation")+"</td>" +"</tr>");
                                       out.println("<tr> <td>"+"Income Tax"+"</td> <td>"+""+"</td> <td> <td>"+"$"+(rx.getFloat("income_tax"))+"</td>" +"</tr>");
                                       out.println("<tr> <td>"+"Medicare"+"</td> <td>"+""+"</td> <td> <td>"+"$"+(rx.getFloat("medicare"))+"</td>" +"</tr>");

                                       out.println("<table> <tr> <th>Bank Name</th> <th>Bank Account Number</th> <th>Net Pay</th> </tr>");
                                       out.println("<tr> <td>"+rs.getString("BankName")+"</td> <td>"+rs.getString("AccNumber")+"</td> <td>"+"$"+(rx.getFloat("expense_claims")+rx.getFloat("basic")-rx.getFloat("superannuation")-rx.getFloat("income_tax")-rx.getFloat("medicare"))+"<td></tr>");
                                       
                                       out.println("<table> <tr> <th>Absence Quota</th> <th></th> <th>Leave Balance</th> </tr>");
                                       out.println("<tr> <td>"+"Annual Leave"+"</td> <td>"+"</td> <td>"+rs.getInt("ALeaveBalance")+" <td>"+"</td>" +"</tr>");
                                       out.println("<tr> <td>"+"Sick Leave"+"</td> <td>"+"</td> <td>"+rs.getInt("SLeaveBalance")+" <td>"+"</td>" +"</tr>");

  
                                       
                                       
                                       
                                       out.println("</table>");
                                       out.println("</div>");
                                       out.println("<br>");
                                       out.println("<br>");
                                       out.println("<button onclick=myFunction() id='button' class='button'>Print</button> <script> function myFunction() { window.print(); } </script>"); 

                                       out.println("</body>");
                                       out.println("</html>");
                                   }
                        	   }
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
                       
                           
                           
                       
                       
                       