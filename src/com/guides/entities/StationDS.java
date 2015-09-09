package com.guides.entities;

import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.guides.models.Direction;

public class StationDS {

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

	// ////////////////////////////////////////////////////////////////////

	public static JSONObject getStationInfo(String source, String distination) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		JSONObject infoJSON = new JSONObject();
		JSONObject stationJson = new JSONObject();

		long sourceline = 0, distinationline = 0, numberOfStations = 0, estimatedTime = 0;
		Direction direction;

		Filter propertyFilter1 = new FilterPredicate("name",
				FilterOperator.EQUAL, source);
		Query q1 = new Query("stations").setFilter(propertyFilter1);
		PreparedQuery pq1 = datastore.prepare(q1);
		Entity sourceEntity = pq1.asSingleEntity();
		String line1 = sourceEntity.getProperty("line").toString();

		Filter propertyFilter2 = new FilterPredicate("name",
				FilterOperator.EQUAL, distination);
		Query q2 = new Query("stations").setFilter(propertyFilter2);
		PreparedQuery pq2 = datastore.prepare(q2);
		Entity distinationEntity = pq2.asSingleEntity();
		String line2 = distinationEntity.getProperty("line").toString();

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
		infoJSON.put("numberOfStation", numberOfStations);
		infoJSON.put("direction", direction);
		infoJSON.put("distination", stationJson);

		return infoJSON;
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

}