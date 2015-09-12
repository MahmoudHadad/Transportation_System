package com.RestResources;
  
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

@Path("/")
@Produces("text/html")
public class Calculator {
	
	@GET
	@Path("calc/{num1}/{num2}") 
	public String getResults(@PathParam("num1") String num1,@PathParam("num2") String num2) {  
		double number1 = Double.parseDouble(num1);
		double number2 = Double.parseDouble(num2);
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("add", number1 + number2);
		resultJSON.put("sub", number1 - number2);
		resultJSON.put("mul", number1 * number2);
		try {
			resultJSON.put("div", number1 / number2);
		} catch(Exception e) {
			resultJSON.put("div", "error");
		}
		return resultJSON.toJSONString();
	}
}
