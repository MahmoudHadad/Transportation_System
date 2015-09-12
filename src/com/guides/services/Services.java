package com.guides.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.mortbay.util.ajax.JSON;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity; 
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.guides.datastore.DatastoreManager;
import com.guides.datastore.ReadCSV;
import com.guides.models.Station;
import com.guides.utilites.Utilities;


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
	@Path("/routing")
	public String routing(@FormParam("StationName") String destination ,@FormParam("Latitude") String latitiude ,
			@FormParam("Longitude") String longitude){
		JSONObject source  = getNearestStationJson(latitiude, longitude);
		System.out.println(source.toJSONString());
		JSONObject StationInfo = Utilities.getStationInfo((String)source.get("StationName") , destination);
		System.out.println(StationInfo.toJSONString());
		StationInfo.put("nearsetStation", source);
		return StationInfo.toJSONString();
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
		Entity selectedStation = Utilities.getNearestStation(userLatitude,
				userLongitude);
		
		JSONObject resultJSON = Utilities.getStationJSON(selectedStation);
		return resultJSON;
	}
	
 
	@POST
	@Path("/getAllDistricts")
	@Produces("text/html")
	
	public static String getAllDistricts()
	{
		ArrayList<String> districts = Utilities.getAllDistricts();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("districts",districts);
		
		return jsonObject.toJSONString();
	}
	
    @Path("/getAreas")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public static String getAreasService(@FormParam("districtName") String districtName){
		   JSONObject object = new JSONObject();
		    object.put("areas",Utilities.getAreas(districtName) );
		    return object.toJSONString();
	    
	}
    @Path("/saveStation")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String saveStation(@FormParam("StationName") String StationName,
			@FormParam("District") String District,@FormParam("Area") String Area,
			@FormParam("Latitude") double Latitude,@FormParam("Longitude") double Longitude,
			@FormParam("Line1") int Line1,@FormParam("Line2") int Line2,
			@FormParam("Line3") int Line3,@FormParam("Line3") int Line4,
			@FormParam("Locations") String Locations)
			 { 
	    JSONObject object = new JSONObject();
	    Station station = new Station(StationName, District,Area,Latitude,Longitude,Line1, Line2,Line3,Line4,
	    		Locations);
	    
	    object.put("save",  Utilities.saveStation(station)); 
	    return object.toJSONString();
		
	}
	@Path("/fillDataStore")
	@GET
	public Response test2(){
		logger.warning("In Test");
		
		try {
			DatastoreManager.clearDatastore();
			ReadCSV.readfile();
			logger.warning("loaded successfully");
		} catch (IOException e) {
			logger.warning("Error in loading " + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(new Viewable("/Test")).build();
		
	}

 
}
