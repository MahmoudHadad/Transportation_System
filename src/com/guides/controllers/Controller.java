package com.guides.controllers;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
 

@Path("/")
@Produces("text/html")
public class Controller {
	private static final Logger logger = Logger.getLogger(Controller.class
			.getCanonicalName());
	
	static{
		logger.warning("In Controller class");
	}
	@GET
	@Path("/testController") 
	public String testController() {
		return "Test Controller";
	}
}