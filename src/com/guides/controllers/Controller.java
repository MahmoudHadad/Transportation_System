package com.guides.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces("text/html")
public class Controller {

	@GET
	@Path("/testController") 
	public static String testController() {
		return "Test Controller";
	}
}