package com.guides.utilites;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.guides.models.Direction;
import com.guides.models.Station;

public class Utilities {

	public static Entity getNearestStation(String userLatitude,
			String userLongitude) {
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Use class Query to assemble a query
		Query q = new Query("MetroStations");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		double minDistance = Double.POSITIVE_INFINITY;
		Entity selectedStation = null;

		for (Entity result : pq.asIterable()) {
			double tempDistance = getDistance(userLatitude, userLongitude,
					result.getProperty("Latitude").toString(), result
							.getProperty("Longitude").toString());
			if (minDistance > tempDistance) {
				selectedStation = result;
				minDistance = tempDistance;
			}
		}
		return selectedStation;
	}

	// ////////////////////////////////////////////////////////////////////

	private static double getDistance(String latitude1, String longitude1,
			String latitude2, String longitude2) {

		double R = 6371000;// Earth mean radius in meteres
		double lat1 = Math.toRadians(Double.parseDouble(latitude1));
		double lat2 = Math.toRadians(Double.parseDouble(latitude2));
		double deltaLat = Math.toRadians(Double.parseDouble(latitude2)
				- Double.parseDouble(latitude1));

		double deltaLon = Math.toRadians(Double.parseDouble(longitude2)
				- Double.parseDouble(longitude1));

		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2)
				* Math.sin(deltaLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double d = R * c;
		return d / 1000;
	}

 

	// ///////////////////////////////////////////////////////////////////
	public static JSONObject getStationJSON(Entity stationEntity) {
		JSONObject resultJSON = new JSONObject();

		resultJSON.put("StationName", stationEntity.getProperty("StationName"));
		resultJSON.put("District", stationEntity.getProperty("District"));
		resultJSON.put("Area", stationEntity.getProperty("Area"));
		resultJSON.put("Latitude", stationEntity.getProperty("Latitude"));
		resultJSON.put("Longitude", stationEntity.getProperty("Longitude"));
		resultJSON.put("Line1", stationEntity.getProperty("Line1"));
		resultJSON.put("Line2", stationEntity.getProperty("Line2"));
		resultJSON.put("Line3", stationEntity.getProperty("Line3"));
		resultJSON.put("Line4", stationEntity.getProperty("Line4"));
		resultJSON.put("Locations", stationEntity.getProperty("Locations"));

		return resultJSON;
	}
	// ////////////////////////////////////////////////////////////////////
	
	public static ArrayList<String> getAllDistricts() 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Use class Query to assemble a query
		Query q = new Query("MetroStations");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		Set<String> district = new HashSet<String>();
		for (Entity result : pq.asIterable()) 
		{
			district.add((String)result.getProperty("District")); 
		}
		ArrayList<String> getAllDistricts = new ArrayList<String>(district);
		
		return getAllDistricts;
	}

	// ////////////////////////////////////////////////////////////////////

	public static JSONObject getStationInfo(String source, String distination) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		JSONObject infoJSON = new JSONObject();
		JSONObject stationJson = new JSONObject();

		long sourceline = 0, distinationline = 0, numberOfStations = 0, estimatedTime = 0;
		Direction direction;

		Filter propertyFilter1 = new FilterPredicate("StationName",
				FilterOperator.EQUAL, source);
		Query q1 = new Query("MetroStations").setFilter(propertyFilter1);
		PreparedQuery pq1 = datastore.prepare(q1);
		System.out.println("source "+source);
		System.out.println("dis "+ distination);
		Entity sourceEntity = pq1.asList(FetchOptions.Builder.withDefaults()).get(0);
		System.out.println("SE" + sourceEntity );
		String line1 = sourceEntity.getProperty("Line2").toString();
		System.out.println("Line1"+line1);
		Filter propertyFilter2 = new FilterPredicate("StationName",
				FilterOperator.EQUAL, distination);
		Query q2 = new Query("MetroStations").setFilter(propertyFilter2);
		PreparedQuery pq2 = datastore.prepare(q2);
		Entity distinationEntity = pq2.asList(FetchOptions.Builder.withDefaults()).get(0);
		String line2 = distinationEntity.getProperty("Line2").toString();
		System.out.println("Line2"+line2);

		stationJson = getStationJSON(distinationEntity);

		sourceline = Long.parseLong(line1);
		distinationline = Long.parseLong(line2);

		if (sourceline > distinationline) {
			numberOfStations = sourceline - distinationline;
			direction = Direction.Monib_Direction;
		} else {
			numberOfStations = distinationline - sourceline;
			direction = Direction.Shoubra_Direction;
		}
		estimatedTime = 3 * numberOfStations;

		infoJSON.put("estimatedTime", estimatedTime);
		infoJSON.put("numberOfStations", numberOfStations);
		infoJSON.put("direction", direction.toString());
		infoJSON.put("distination", stationJson);

		return infoJSON;
	}
	// ////////////////////////////////////////////////////////////////////
	public static JSONArray getAreas(String districtName){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		 
		JSONArray areas = new JSONArray();
		
		Filter propertyFilter1 =
				  new FilterPredicate("District",FilterOperator.EQUAL,districtName);
		Query q1 = new Query("MetroStations").setFilter(propertyFilter1);
		PreparedQuery pq1 = datastore.prepare(q1);
		List<Entity> districtsEntity =  pq1.asList(FetchOptions.Builder.withDefaults());
		for(int i = 0 ; i < districtsEntity.size();i++){
			JSONObject district = new JSONObject ();
			String Area = districtsEntity.get(i).getProperty("Area").toString();
			String StationName = districtsEntity.get(i).getProperty("StationName").toString();
			String Locations = districtsEntity.get(i).getProperty("Locations").toString();
		
			district.put("Area", Area);
			district.put("StationName", StationName);
			district.put("Locations", Locations);
			areas.add(district);
		}
		return areas;
		//districtsEntity.get(0).getProperty("propertyName");
		//Entity sourceEntity = pq1.asSingleEntity();
		//String area = sourceEntity.getProperty("area").toString();
	}
	////////////////////////////////////////////////////////////////////////////
	public static Boolean saveStation(Station station){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
 
		
		Entity stationEntity = new Entity("MetroStations" );
		
		stationEntity.setProperty("StationName", station.StationName);
		stationEntity.setProperty("District", station.District);
		stationEntity.setProperty("Area", station.Area);
		stationEntity.setProperty("Latitude", station.Latitude);
		stationEntity.setProperty("Longitude", station.Longitude);
		stationEntity.setProperty("Line1", station.Line1);
		stationEntity.setProperty("Line2", station.Line2);
		stationEntity.setProperty("Line3", station.Line3);
		stationEntity.setProperty("Line4", station.Line4);
		stationEntity.setProperty("Locations", station.Locations);
		datastore.put(stationEntity);
		return true;
		
	}
}