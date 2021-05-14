package Com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Payment;


public class Payment_API {

	@WebServlet("/Payment_API")
	public class OrderAPI extends HttpServlet {
		private static final long serialVersionUID = 1L;

		
		Payment payment_obj=new Payment();
		
		public Payment_API() {
			super();
			// TODO Auto-generated constructor stub
		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			// TODO Auto-generated method stub
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			// TODO Auto-generated method stub
			String outputString = payment_obj.createPayment(
					request.getParameter("CardName"),
					request.getParameter("CardNo"), 
					request.getParameter("ZipCode"), 
					request.getParameter("BID"),
					request.getParameter("CID"));

			response.getWriter().write(outputString);
		}	

		protected void doPut(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			Map paras = getParasMap(request);

			String outputString =  payment_obj.updateItem(
					paras.get("itemID").toString(),
					paras.get("name").toString(),
					paras.get("cardno").toString(), 
					paras.get("zipcode").toString(), 
					paras.get("bid").toString(),
					paras.get("cid").toString()); 

			response.getWriter().write(outputString);
		}
		
		protected void doDelete(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			// TODO Auto-generated method stub
			Map paras = getParasMap(request);
			String output = payment_obj.deletepayment(paras.get("itemID").toString());
			response.getWriter().write(output); 		
		}

		// Convert request parameters to a Map
		private Map getParasMap(HttpServletRequest request) {
			
			Map<String, String> map = new HashMap<String, String>();
			
			try {			
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				
				String[] params = queryString.split("&");
				for (String param : params) {
					String[] p = param.split("=");
					map.put(p[0], p[1]);
				}
				
			} catch (Exception e) {
			  }
			
			return map;
		}
	}
