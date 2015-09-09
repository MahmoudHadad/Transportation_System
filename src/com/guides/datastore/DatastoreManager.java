package com.guides.datastore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.guides.models.Station;


public class DatastoreManager {
	
	public static	Boolean insertStation(Station station){
			if ((station.getStationName())==null) {
				return false;
			}
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
			Entity myStation = new Entity("Station");
			
			
			myStation.setProperty("StationName", station.getStationName());
			myStation.setProperty("District",station.getDistrict());
			myStation.setProperty("Area", station.getArea());
			myStation.setProperty("Latitude", station.getLatitude());
			myStation.setProperty("Longitude", station.getLongitude());
			myStation.setProperty("Line1", station.getLine1());
			myStation.setProperty("Line2", station.getLine2());
			myStation.setProperty("Line3", station.getLine2());
			myStation.setProperty("Line4", station.getLine2());
			myStation.setProperty("Locations", station.getLocations());
			datastore.put(myStation);
			station.setId(String.valueOf(myStation.getKey().getId()));
			return true;
		}

	public static Boolean clearDatastore(){
		try{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// delete old data from datastore
			Query hashTageQuery = new Query("Station");
			PreparedQuery pq = datastore.prepare(hashTageQuery);
				
			for(Entity e : pq.asIterable())
				datastore.delete(e.getKey());
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	}


