package com.guides.controllers;

import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.Entity;
import com.guides.utilites.Utilities;


@Path("/")
@Produces("text/html")
public class Controller {
	
	private static final Logger logger = Logger.getLogger(Controller.class
			.getCanonicalName());
	
	static{
		logger.warning("In Services class");
	}
	@Path("/test")
	@GET
	public Response test(){
		logger.warning("In Test");
		return Response.ok(new Viewable("/Test")).build();
		
	}
	@Path("getAllDistricts")
	@POST
	public String getAllDistricts() {
		
		return "Cairo|Giza|Asuan|Sues";
		
	}
	@Path("getAreas")
	@POST
	public String getAreas(@FormParam("districtName") String districtName) {
		
		return districtName;
		
	}
	

}
