package com.guides.datastore;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import com.csvreader.CsvReader;
import com.google.appengine.api.appidentity.AppIdentityService.ParsedAppId;
import com.guides.models.Station;
import com.guides.services.Services;

public class ReadCSV {
	private static final Logger logger = Logger.getLogger(Services.class
			.getCanonicalName());
	
	
	public static void readfile() throws IOException {

		logger.warning("in readfiles");
		CsvReader csvReader = new CsvReader("MetroStations.csv", ',',
				Charset.forName("UTF-8"));
		logger.warning("in readfiles   after read file");
		
		csvReader.setSkipEmptyRecords(true);
		csvReader.readHeaders();
		
		String[] headers = csvReader.getHeaders();

		for (String head : headers)
			System.out.println(head);
				
		while (csvReader.readRecord()) {
			Station s = new Station();
			s.setStationName(csvReader.get("StationName"));
			
			s.setDistrict(csvReader.get("District"));
			s.setArea(csvReader.get("Area"));
			s.setLatitude(Double.parseDouble(csvReader.get("Latitude")));
			s.setLongitude(Double.parseDouble(csvReader.get("Longitude")));
			s.setLine1(Integer.parseInt(csvReader.get("Line1")));
			s.setLine2(Integer.parseInt(csvReader.get("Line2")));
			s.setLine3(Integer.parseInt(csvReader.get("Line3")));
			s.setLine4(Integer.parseInt(csvReader.get("Line4")));
			s.setLocations(csvReader.get("Locations"));

			Boolean b = DatastoreManager.insertStation(s);

			System.out.println(b + " :true");
			
//			if (!c.get("Latitude").isEmpty()) {
//				Latitude = Double.parseDouble();
//			}
		
		}
		
		
//		CSVReader reader = new CSVReader(new FileReader(
//				"Metro Stations - Sheet1.csv"), ',', '"', 1);
//
//		// Read CSV line by line and use the string array as you want
//		String[] nextLine = null;
//		try {
//			while ((nextLine = reader.readNext()) != null) {
//				Station s = new Station();
//				if (nextLine != null) {
//					// Verifying the read data here
//					s.setStationName(nextLine[0]);
//					s.setDistrict(nextLine[1]);
//					s.setArea(nextLine[2]);
//					s.setLatitude(Double.parseDouble(nextLine[3]));
//					s.setLongitude(Double.parseDouble(nextLine[4]));
//					s.setLine1(Integer.parseInt(nextLine[5]));
//					s.setLine2(Integer.parseInt(nextLine[6]));
//					s.setLine3(Integer.parseInt(nextLine[7]));
//					s.setLine4(Integer.parseInt(nextLine[8]));
//					s.setLocations(nextLine[9]);
//
//					DataStoreAdd data = new DataStoreAdd();
//					Boolean b = data.insertStation(s);
//
//					System.out.println(b + " :true");
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
