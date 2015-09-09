package com.guides.models;
//model or java beans
public class Station {
	private String Id;
	private String StationName ;
	private String District ;
	private String Area ;
	private double Latitude;
	private double Longitude ;
	private int Line1; 
	private int Line2; 
	private int Line3; 
	private int Line4; 
	private String Locations;
	public String getStationName() {
	return StationName;
	}
	public Station() {}
	
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public String getDistrict() {
		return District;
	}
	public void setDistrict(String district) {
		District = district;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public int getLine1() {
		return Line1;
	}
	public void setLine1(int line1) {
		Line1 = line1;
	}
	public int getLine2() {
		return Line2;
	}
	public void setLine2(int line2) {
		Line2 = line2;
	}
	public int getLine3() {
		return Line3;
	}
	public void setLine3(int line3) {
		Line3 = line3;
	}
	public int getLine4() {
		return Line4;
	}
	public void setLine4(int line4) {
		Line4 = line4;
	}
	public String getLocations() {
		return Locations;
	}
	public void setLocations(String locations) {
		Locations = locations;
	}
	public Station(String stationName,
			String district,
			String area,
			double latitude,
			double longitude,
			int line1,
			int line2,
			int line3,
			int line4,
			String locations) {
		super();
		StationName = stationName;
		District = district;
		Area = area;
		Latitude = latitude;
		Longitude = longitude;
		Line1 = line1;
		Line2 = line2;
		Line3 = line3;
		Line4 = line4;
		Locations = locations;
	}
	public void setId(String valueOf) {
		// TODO Auto-generated method stub
	Id = valueOf ;	
		
	}
	public String getId() {
		return Id;
	}
	@Override
	public String toString() {
		return "Station [Id=" + Id + ", StationName=" + StationName
				+ ", District=" + District + ", Area=" + Area + ", Latitude="
				+ Latitude + ", Longitude=" + Longitude + ", Line1=" + Line1
				+ ", Line2=" + Line2 + ", Line3=" + Line3 + ", Line4=" + Line4
				+ ", Locations=" + Locations + "]";
	}
	
	
}
