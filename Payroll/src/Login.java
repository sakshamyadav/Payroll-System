import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Login extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //get user/password
        String employee_id = request.getParameter("employee_id");
        String password = request.getParameter("password");    
        
        HttpSession session = request.getSession(); 
        session.setAttribute("employeeid", employee_id);
        //check with ValidateLogin class to see if valid
        if(ValidateLogin.user(employee_id, password)) { 
        	RequestDispatcher rs = request.getRequestDispatcher("PersonalInfoOutput");
            rs.forward(request, response);
        }
        else //if login unsuccessful 
        {
           out.print("Employee ID or Password is incorrect. Please try again.");
           RequestDispatcher rs = request.getRequestDispatcher("index.html");
           rs.include(request, response);
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

