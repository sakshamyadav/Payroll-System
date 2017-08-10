import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class NewPassword extends HttpServlet { 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    			response.setContentType("text/html;charset=UTF-8");
    			PrintWriter out = response.getWriter();
    			//get parameters
    			String oldpassword = request.getParameter("oldpassword"); 
    			String newpassword = request.getParameter("newpassword"); 
    			String confirmpassword = request.getParameter("confirmpassword"); 
    			//new password variable
    			int newpass = newpassword.length(); 
    			//get employeeid of currently logged in user
    			HttpSession session = request.getSession(false);
    			String employeeid = ""; 
    			
                if(session != null) { 
                    employeeid = (String)session.getAttribute("employeeid"); 
                }
                //connect to database to check if oldpassword is correctly matching user input
                boolean st = false; 
                try { 
                	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                	PreparedStatement ps = con.prepareStatement("SELECT pwd FROM payroll_system.employee_login WHERE employeeID = ?");
                	ps.setString(1, employeeid);
                	ResultSet rs = ps.executeQuery(); 
                	st = rs.next(); 
              		String currentpassword = rs.getString("pwd"); // get current password for checking
                	if(st) {  //logic to check if passwords match, and based on that, update user's password
                		
                		if((currentpassword.equals(oldpassword)) && newpassword.equals(confirmpassword) && newpass >= 8) { 
                			PreparedStatement pd = con.prepareStatement("UPDATE payroll_system.employee_login SET pwd = ? where employeeID = ?" ); 
                			pd.setString(1, newpassword);
                			pd.setString(2, employeeid);
                			int updated = pd.executeUpdate(); 
                			if(updated > 0){
                				out.println("Password Successfully Updated");
                	           	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                			}
                		}

                		else if(oldpassword.equals("") || newpassword.equals("")  || confirmpassword.equals("") ) { 
                			out.println("Fields cannot be left blank.");
                          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                		}
                		else if(!currentpassword.equals(oldpassword) && !newpassword.equals(confirmpassword)) { 
                			out.println("Old password is incorrect and passwords do not match, please try again.");
                          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                		}
                		else if(!currentpassword.equals(oldpassword)) { 
                			out.println("Old password entered incorrectly, please try again.");
                          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                		}
                		else if(!newpassword.equals(confirmpassword)) { 
                			out.println("Passwords do not match, please try again.");
                          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                		}
                		else if(newpass < 8 ) {
                			out.println("New Password must be atleast 8 characters in length.");
                          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");

                		}


                		
                    }
                }catch(Exception e)
                 {
                     e.printStackTrace();
                 }
               out.close();
       }
   }
                	
                
    