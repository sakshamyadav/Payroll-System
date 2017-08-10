import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ManageEmployeeRedirect extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //get parameters to see which button was pressed
        String button1 = request.getParameter("ID1");
        String button2 = request.getParameter("ID2"); 
        String button3 = request.getParameter("ID3"); 
        //check to see which button was pressed and act accordingly
        if(button1 != null) {
        	RequestDispatcher rs = request.getRequestDispatcher("NewEmployee.html"); 
        	rs.forward(request, response);
        }
        else if(button2 != null) { 
        	RequestDispatcher rs = request.getRequestDispatcher("ViewEmployee.html"); 
        	rs.forward(request, response);
        }
        else if(button3 != null) { 
        	RequestDispatcher rs = request.getRequestDispatcher("UpdateEmployee.html"); 
        	rs.forward(request, response);
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

        
        
