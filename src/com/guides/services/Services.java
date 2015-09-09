package com.guides.services;

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
import com.guides.entities.StationDS;


@Path("/")
@Produces("text/html")
public class Services {
	
	private static final Logger logger = Logger.getLogger(Services.class
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
	
	@POST
	@Path("/getNearestStation")
	public String getNearestStation(@FormParam("userLatitude") String userLatitude, 
			@FormParam("userLongitude") String userLongitude ) {
		 
		JSONObject resultJSON = getNearestStationJson(userLatitude,
				userLongitude);  
		return resultJSON.toJSONString();
	}

	private JSONObject getNearestStationJson(String userLatitude,
			String userLongitude) {
		Entity selectedStation = StationDS.getNearestStation(userLatitude,
				userLongitude);
		
		JSONObject resultJSON = StationDS.getStationJSON(selectedStation);
		return resultJSON;
	}
	
	@Path("/test2")
	@GET
	public Response test2(){
		logger.warning("In Test");
		return Response.ok(new Viewable("/Test")).build();
		
	}
}
