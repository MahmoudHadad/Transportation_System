package com.RestResources;
  
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;

 





import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;







import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.apache.tools.ant.types.resources.Sort;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.Query.FilterPredicate;

@Path("/")
public class Haversine {
	@POST 
	@Path("/saveStation")
	@Produces("text/html") 
	public String saveStation(@FormParam("lat") String lat,  @FormParam("lon") String lon ) {
		 
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity station = new Entity("MetroStations");

		station.setProperty("Latitude", lat);
		station.setProperty("Longitude", lon);
		
		 datastore.put(station);
		return "test";
	}
	
	@POST
	@Path("/getNearestStation")
	@Produces("text/html")
	public String getNearestStation(@FormParam("userLatitude") String userLatitude, 
			@FormParam("userLongitude") String userLongitude ) {
		 
		Entity selectedStation = getNearestStationFunction(userLatitude,
				userLongitude);
		
		JSONObject resultJSON = getStationJSON(selectedStation);  
		return resultJSON.toJSONString();
	}

	public Entity getNearestStationFunction(String userLatitude,
			String userLongitude) {
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use class Query to assemble a query
		Query q = new Query("MetroStations");
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);;
		
		double minDistance = Double.POSITIVE_INFINITY;
		Entity selectedStation = null;
		
		for(Entity result : pq.asIterable() ) {
			double tempDistance = getDistance(userLatitude, userLongitude, 
					result.getProperty("Latitude").toString(),
					result.getProperty("Longitude").toString()); 
			if(minDistance > tempDistance ) {
				selectedStation = result;
				minDistance = tempDistance;
			}
		}
		return selectedStation;
	}
	private double getDistance(String latitude1, String longitude1, String latitude2,  String longitude2 ) {

		double  R = 6371000 ;// Earth mean radius in meteres
		double lat1 = Math.toRadians( Double.parseDouble(latitude1) ); 
		double lat2 = Math.toRadians( Double.parseDouble(latitude2) ); 
		double deltaLat = Math.toRadians( Double.parseDouble(latitude2) - Double.parseDouble(latitude1)); ;
		double deltaLon = Math.toRadians( Double.parseDouble(longitude2) - Double.parseDouble(longitude1));
 
		double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
		        Math.cos(lat1) * Math.cos(lat2) *
		        Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
 
		double d = R * c;
		return d / 1000;
	}
	private JSONObject getStationJSON(Entity stationEntity ) {
		
		JSONObject resultJSON = new JSONObject(); 
		
//		resultJSON.put("StationName", stationEntity.getProperty("StationName"));
//		resultJSON.put("District", stationEntity.getProperty("District"));
//		resultJSON.put("Area", stationEntity.getProperty("Area"));
		resultJSON.put("Latitude", stationEntity.getProperty("Latitude"));
		resultJSON.put("Longitude", stationEntity.getProperty("Longitude"));
//		resultJSON.put("Line1", stationEntity.getProperty("Line1"));
//		resultJSON.put("Line2", stationEntity.getProperty("Line2"));
//		resultJSON.put("Line3", stationEntity.getProperty("Line3"));
//		resultJSON.put("Line4", stationEntity.getProperty("Line4"));
//		resultJSON.put("Locations", stationEntity.getProperty("Locations"));
		
		return resultJSON;
	}
	@POST
	@Path("/testFilter")
	@Produces("text/html")
	public String testFilter() {
		 
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter testFilter1 = new FilterPredicate("Latitude",FilterOperator.EQUAL,"30.03579");
		// Use class Query to assemble a query
		Query q = new Query("MetroStations").setFilter(testFilter1);
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);;
		Entity result = pq.asSingleEntity();
		//getProperty("Longitude");
		return result.getProperty("Longitude").toString();
	}
	@POST
	@Path("/testSort")
	@Produces("text/html")
	public String testSort() {
		 
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 
		// Use class Query to assemble a query
		Query q = new Query("MetroStations").addSort("Longitude",SortDirection.DESCENDING);
		
		// Use PreparedQuery interface to retrieve results
		JSONObject obj = new JSONObject();
		PreparedQuery pq = datastore.prepare(q);;
		
		for( Entity result : pq.asIterable() ) {
			System.out.println(result.getProperty("Longitude"));
			obj.put(result.getProperty("Longitude"),  result.getProperty("Longitude"));
		}
		//getProperty("Longitude");
		return obj.toJSONString();
	}
}

//Haversine
//formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
//c = 2 ⋅ atan2( √a, √(1−a) )
//d = R ⋅ c
//where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
//note that angles need to be in radians to pass to trig functions!
