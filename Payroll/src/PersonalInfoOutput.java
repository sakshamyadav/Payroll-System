import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PersonalInfoOutput extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            // get employee id of logged in employee
            HttpSession session = request.getSession(false);
            String employeeid = ""; 

                       if(session != null) { 
                           employeeid = (String)session.getAttribute("employeeid"); 
                       }

                    //select details of employee from table in DB
            boolean st = false;
            try { 
                Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                PreparedStatement ps = con.prepareStatement("select employeeID,  FirstName, LastName, Admin, DOB, Address, Email, HourlyRate, Gender, ALeaveBalance, SLeaveBalance, ActiveStatus, Role, BSB, BankName, AccNumber, SuperNumber, SuperCompany from payroll_system.employee_info where employeeID = ?");
                ps.setString(1, employeeid);
                ResultSet rs = ps.executeQuery(); 
                st = rs.next(); 
                if(st){
                boolean adminTrue = rs.getBoolean("Admin"); 
                boolean activeTrue = rs.getBoolean("ActiveStatus"); 
                //use html to output employee's details
                out.println("<html>");
                out.println("<head>");
                out.println("<style>"); 
                out.println("table { border-collapse: collapse; width: 50%; } th, td { text-align: left; padding: 8px; } tr:nth-child(even){background-color: #f2f2f2}");
                out.println("tr:hover {background-color: #e2f4ff;}");
                out.println("</style>");
                out.println("<link rel = stylesheet type = text/css href = main.css>");
                out.println("<link rel = stylesheet type = text/css href = sidebar.css>");
                out.println("<title>Personal Information</title>");
                out.print("</head>");
                
                out.println("<body>");
                
                if(adminTrue){ 
                	out.println("<ul>");
                	out.println("<li><a class=active >View Personal Information</a></li>");
                	out.println("<li><a href=ViewEmployeeExpenseClaim>View Expense Claims</a></li>");
                	out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
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
                    out.println("<li><a class=active href=PersonalInfoOutput>View Personal Information</a></li>");
                    out.println("<li><a href=ViewNonAdminExpenseClaim>View Expense Claims</a></li>");
                    out.println("<li><a href=PayslipAdmin>View Payslips</a></li>");
                    out.println("<li><a href=NonAdminChangePass.html>Change Password</a></li>");
                	out.println("<li><a href=NonAdminTimeSheet.html>Maintain Timesheet</a></li>");
                	out.println("<li><a href=NonAdminExpenses.html>Maintain Expenses</a></li>");
                    out.println("</ul>");
                	out.println("<div style=margin-left:25%;padding:1px 16px;height:1000px;>");
                	out.println("</div>");
                	
                }
                
                out.println("<div id=container>");
                out.println("<form align=right name=form1 method=post action=index.html> <label> <input name=submit2 type=submit id=submit2 value=Logout> </label> </form>");
                out.println("<h1>Personal Information</h1>");
                out.println("<table border =1>");
                
                out.println("<tr>");
                out.println("<td>Name</td>");
                out.println("<td>"+ rs.getString("FirstName") + " " + rs.getString("LastName") + "</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Employee ID</td>");
                out.println("<td>"+ rs.getString("employeeID")+"</td>");
                out.println("</tr>");
                
                if(adminTrue) { 
                    out.println("<tr>");
                    out.println("<td>Admin</td>");
                    out.println("<td>Yes</td>");
                    out.println("</tr>");
                }
                else{ 
                    out.println("<tr>");
                    out.println("<td>Admin</td>");
                    out.println("<td>No</td>");
                    out.println("</tr>");
                }
                
                out.println("<tr>");
                out.println("<td>Date Of Birth</td>");
                out.println("<td>"+ rs.getString("DOB")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Residential Address</td>");
                out.println("<td>"+ rs.getString("Address")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Email</td>");
                out.println("<td>"+ rs.getString("Email")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Hourly Income</td>");
                out.println("<td>"+ "$" + rs.getString("HourlyRate")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Gender</td>");
                out.println("<td>"+ rs.getString("Gender")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Annual Leave Balance</td>");
                out.println("<td>"+ rs.getString("ALeaveBalance")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Sick Leave Balance</td>");
                out.println("<td>"+ rs.getString("SLeaveBalance")+"</td>");
                out.println("</tr>");
                
                if(activeTrue) { 
                    out.println("<tr>");
                    out.println("<td>Currently Active</td>");
                    out.println("<td>Yes</td>");
                    out.println("</tr>");
                }
                else { 
                    out.println("<tr>");
                    out.println("<td>Currently Active</td>");
                    out.println("<td>No</td>");
                    out.println("</tr>");
                }
                
                out.println("<tr>");
                out.println("<td>Role</td>");
                out.println("<td>"+ rs.getString("Role")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>BSB Number</td>");
                out.println("<td>"+ rs.getString("BSB")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Bank Name</td>");
                out.println("<td>"+ rs.getString("BankName")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Bank Account Number</td>");
                out.println("<td>"+ rs.getString("AccNumber")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Superannuation Company</td>");
                out.println("<td>"+ rs.getString("SuperCompany")+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>Superannuation Number</td>");
                out.println("<td>"+ rs.getString("SuperNumber")+"</td>");
                out.println("</tr>");
                
                out.println("</table>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
                
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
