package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.*;


public class LocationDAO extends DAO {
	public LocationDAO() {
		super();
	}
	
	public ArrayList<Location> searchLocations(String tourId) {
		String sql = "SELECT * FROM location WHERE tourId = ?";
		ArrayList<Location> locations = new ArrayList<Location>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.valueOf(tourId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Location location = new Location();
				location.setId(rs.getInt("id"));
				location.setTourId(rs.getInt("tourId"));
				location.setLocation(rs.getNString("location"));
				locations.add(location);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return locations;
	}
}
