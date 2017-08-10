import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date; 

public class PayrollRun extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
        	try{ 
    			//connect with database 
   			 Class.forName("com.mysql.jdbc.Driver").newInstance(); 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_system", "root", ""); 
                //delete all elements from payslip table
                Statement truncate = con.createStatement();
                truncate.executeUpdate("TRUNCATE payroll_system.payslip");

                //insert employeeID into payslip table
                PreparedStatement ps = con.prepareStatement("INSERT INTO payroll_system.payslip(employeeID) SELECT employeeID FROM payroll_system.employee_info");
                ps.executeUpdate(); 
                
                //insert date of payroll invocation
                String paydate = new SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date());
                PreparedStatement ps0= con.prepareStatement("UPDATE payroll_system.payslip SET paydate=?");
                ps0.setString(1, paydate);
                ps0.executeUpdate(); 
                //update payslip table
                PreparedStatement ps1 = con.prepareStatement("UPDATE payroll_system.payslip pslip JOIN payroll_system.employee_info info ON pslip.employeeID = info.employeeID SET pslip.role = info.Role ");
                ps1.executeUpdate(); 
                
                PreparedStatement ps2 = con.prepareStatement("UPDATE payroll_system.payslip SET run =?");
                ps2.setString(1, "1");
                ps2.executeUpdate(); 
                
                //select values from employee_info table
                PreparedStatement ps3 = con.prepareStatement("SELECT HourlyRate, employeeID FROM payroll_system.employee_info");
            ResultSet rs = ps3.executeQuery(); 
            //for all employees present on the system, perform the following calculations
            while(rs.next()) {
                float hourlyRate = rs.getFloat("HourlyRate");
                String employee_id = rs.getString("employeeID"); 
                
                PreparedStatement ps4 = con.prepareStatement("SELECT Sum(Hours) as sum FROM payroll_system.monthly_timesheet WHERE employeeID = ?");
                ps4.setString(1, employee_id);
                ResultSet rt = ps4.executeQuery(); 
                //get hours worked and hourly pay rate to calculate salary
                if(rt.next()){
                float hours_worked = rt.getFloat("sum"); 
                
                
                float basic_pay = (hours_worked*hourlyRate); 
                
                int converted_basic_pay = Math.round(basic_pay);
                //update payslip table with employees' incomes
                PreparedStatement ps5 = con.prepareStatement("UPDATE payroll_system.payslip SET basic = ? WHERE employeeID =?"); 
                ps5.setFloat(1, basic_pay);
                ps5.setString(2, employee_id);
                ps5.executeUpdate();
                //calculate tax
                float taxPercentage = 0;
                PreparedStatement ps6 = con.prepareStatement("SELECT TaxPercentage from payroll_system.tax_info WHERE ? BETWEEN FromSalary and COALESCE(ToSalary,1000000000000000000)");
                ps6.setInt(1, converted_basic_pay);
                ResultSet rx = ps6.executeQuery();
                if (rx.next()){ 
    //check to see which tax bracket the employee falls in
                	PreparedStatement ps7 = con.prepareStatement("SELECT MAX(TaxPercentage) as maxTax, MAX(FromSalary) as maxFrom from payroll_system.tax_info"); 
                    ResultSet rx1 = ps7.executeQuery(); 
                    if(rx1.next()){
                    	float maxTax = rx1.getFloat("maxTax");
                    	float maxFrom = rx1.getFloat("maxFrom");
                    	if(converted_basic_pay>maxFrom){
                    		taxPercentage = maxTax;
                    	}
                    	else{
                    		taxPercentage =rx.getFloat("TaxPercentage");
                    	}
                        float actual_tax = taxPercentage/100;
                        float income_tax = converted_basic_pay*actual_tax;
                     
                        float  superannuation = converted_basic_pay*19/200;
                        float medicare = converted_basic_pay*2/100;// calculate superannuation/medicare levvy 
                        
                        PreparedStatement ps8 = con.prepareStatement("UPDATE payroll_system.payslip SET income_tax = ?, medicare = ?, superannuation = ? WHERE employeeID = ?");
                      
                        ps8.setFloat(1, income_tax);
                        ps8.setFloat(2, medicare);
                        ps8.setFloat(3, superannuation);
                        ps8.setString(4, employee_id);
                        ps8.executeUpdate();
                        // updated payslip table with expense claims 
                        PreparedStatement ps9 = con.prepareStatement("UPDATE payroll_system.payslip SET expense_claims = (SELECT SUM(Expense) FROM payroll_system.expense_master WHERE employeeID=?) WHERE employeeID=?");
                        ps9.setString(1, employee_id);
                        ps9.setString(2, employee_id);
                        ps9.executeUpdate();
                        
                        PreparedStatement ps10 = con.prepareStatement("UPDATE payroll_system.payslip as t1 SET hours_worked = (SELECT SUM(Hours) FROM payroll_system.monthly_timesheet WHERE employeeID=t1.employeeID) WHERE employeeID=?");
                        ps10.setString(1, employee_id);
                        ps10.executeUpdate();
                    }
                    
                }


              }
           }// delete data from expense_master and monthly_timesheet tables
            
            Statement truncate1 = con.createStatement();
            truncate1.executeUpdate("TRUNCATE payroll_system.expense_master");     
            
            Statement truncate2 = con.createStatement();
            truncate2.executeUpdate("TRUNCATE payroll_system.monthly_timesheet");
         
            out.println("Payroll has successfully been executed. Payslips are now available for the current period and reports may be generated");
          	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");   
        	
                	
                
                
                

                // if connection cannot be established with database
    }catch(Exception e)
    {
    	out.println("Error, please go back");
      	 out.print("<FORM><INPUT Type=button VALUE=Back onClick=history.go(-1);return true;></FORM>");
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

