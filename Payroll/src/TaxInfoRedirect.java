import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TaxInfoRedirect extends HttpServlet { 
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // get info on which button was pressed
        String button1 = request.getParameter("ID1");
        String button2 = request.getParameter("ID2"); 
        //action according to which button was pressed. 
        if(button1 != null) {
        	RequestDispatcher rs = request.getRequestDispatcher("ViewTaxInfo"); 
        	rs.forward(request, response);
        }
        else if(button2 != null) { 
        	RequestDispatcher rs = request.getRequestDispatcher("EditTaxInfo.html"); 
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

        
        

