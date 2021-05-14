package Com;

import model.Payment;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment_")
public class Payment_service {
	Payment payment_obj=new Payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts()
	{
		return payment_obj.read_payment();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createpayment(
			@FormParam("CardName") String cardName,
			@FormParam("CardNo") int cardNo,			
			@FormParam("ZipCode") int zipCode,
			@FormParam("BID") int bID,
			@FormParam("CID") int cID) {
		String output= payment_obj.createPayment(cardName, cardNo, zipCode, bID, cID); //date issue
				return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String postData)
	{
		//Convert the input string to a JSON object
		JsonObject paymentObj = new JsonParser().parse(postData).getAsJsonObject();
		
		String ID = paymentObj.get("itemID").getAsString();
		String name = paymentObj.get("name").getAsString();
		int cardno = paymentObj.get("cardno").getAsInt();
		int zipcode = paymentObj.get("zipcode").getAsInt();
		int bid = paymentObj.get("bid").getAsInt();
		int cid = paymentObj.get("cid").getAsInt();

		
		
		String output=payment_obj.updatepayment(ID, name, cardno, zipcode, bid, cid);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
		Document doc= Jsoup.parse(itemData,"",Parser.xmlParser());
		
		String itemID=doc.select("ItemID").text();
		
		String output=payment_obj.deletepayment(itemID);
		return output;
	}
}
