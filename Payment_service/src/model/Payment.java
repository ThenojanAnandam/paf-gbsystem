package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Payment {
	private Connection connect()
	{
		Connection con=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide Correct Database Details
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test_", "root", "");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
	
	public String createPayment(String name,int cardno, int zipcode,int bid,int cid) {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			LocalDate date= LocalDate.now();
			LocalTime time= LocalTime.now();
			String  query= "insert into Paymentdb(name,cardno,zipcode,bid,cid,payment_date,payment_time)"
							+
							" values(?,?,?,?,?,?,?) ";
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, name);
			ps.setInt(2, cardno);
			ps.setInt(3, zipcode);
			ps.setInt(4, bid);
			ps.setInt(5, cid);
			ps.setString(6, date.toString());
			ps.setString(7, time.toString());
			
			ps.execute();
			con.close();
			
			output="Insert Success";
		}
		catch (Exception e) {
			// TODO: handle exception
			output="Error while inserting the Item";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	public String read_payment() {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			output="<table><tr><th>Payment ID</th><th>Card Name</th><th>Card No</th><th>Zip Code</th><th>BID</th><th>CID</th><th>Payment Date</th><th>Payment Time</th><th>Update</th><th>Remove</th></tr>";
			String query="select * from Paymentdb";
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery(query);
			
			while(rs.next())
			{
				String Payment_id= Integer.toString(rs.getInt("id"));
				String name= rs.getString("name");
				int cardno = rs.getInt("cardno");
				int zipcode = rs.getInt("zipcode");
				int bid = rs.getInt("bid");
				int cid = rs.getInt("cid");
				String date = rs.getString("");// How to Get Date as A String - Doubt
				String time = rs.getString("");// How to Get Date as A String - Doubt
				
				output +="<tr><td>"+Payment_id+"</td>";
				output +="<tr><td>"+name+"</td>";
				output +="<tr><td>"+cardno+"</td>";
				output +="<tr><td>"+zipcode+"</td>";
				output +="<tr><td>"+bid+"</td>";
				output +="<tr><td>"+cid+"</td>";
				output +="<tr><td>"+date+"</td>";
				output +="<tr><td>"+time+"</td>";
				
				output +="<td><input name=\\\"btnUpdate\\\" type=\\\"button\\\" \r\n" + 
						" value=\\\"Update\\\" class=\\\"btn btn-secondary\\\"></td>\"\r\n" + 
						" + \"<td><form method=\\\"post\\\" action=\\\"posts.jsp\\\">\"\r\n" + 
						" + \"<input name=\\\"btnRemove\\\" type=\\\"submit\\\" value=\\\"Remove\\\" \r\n" + 
						" class=\\\"btn btn-danger\\\">\"\r\n" + 
						" + \"<input name=\\\"id\\\" type=\\\"hidden\\\" value=\\\"\" + id\r\n" + 
						" + \"\\\">\" + \"</form></td></tr>";
				
			}
			con.close();
			output+="</tabel>";
		} catch (Exception e) {
			// TODO: handle exception
			output="Error while reading the Posts";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String updatepayment(String ID,String name,int cardno,int zipcode,int bid, int cid) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 LocalDate date= LocalDate.now();
	 LocalTime time= LocalTime.now();
	 String query = "UPDATE Paymentdb SET name=?,cardno=?,zipcode=?,bid=?,cid=?,payment_date=?,payment_time=? WHERE id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, name); 
	 preparedStmt.setInt(2,cardno);
	 preparedStmt.setInt(3,zipcode); 
	 preparedStmt.setInt(4,bid);
	 preparedStmt.setInt(5,cid);
	 preparedStmt.setString(6, date.toString()); 
	 preparedStmt.setString(7, time.toString()); 
	 preparedStmt.setInt(8, Integer.parseInt(ID)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Updated successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while updating the item."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	
	public String deletepayment(String ID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from Paymentdb where id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(ID)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Deleted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while deleting the item."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	

}
