package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



import java.util.*;

// thư viện  sử lý thời gian :
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Bill;
import model.Tour;
import model.Client;
import java.sql.SQLException;

public class BillDAO extends DAO{
	
	public BillDAO() {
		super();
	}
	
	public boolean updateBill(Tour tour, Client client) {
	    boolean check = false;
	    String sqlInsert = "INSERT INTO bill (clientId, tourId, total, note, paymentDate, saleOff, bookedPeople) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    String sqlUpdateTour = "UPDATE tour SET startDate = ?, endDate = ? WHERE id = ?";

	    try {
	        con.setAutoCommit(false); // Bắt đầu transaction

	        PreparedStatement psInsert = con.prepareStatement(sqlInsert);
	        psInsert.setInt(1, client.getId());
	        psInsert.setInt(2, tour.getId());
	        psInsert.setFloat(3, tour.getPrice() * tour.getBookedPeople() * (1 - tour.getSaleOff()));
	        psInsert.setString(4, tour.getNote());
	        psInsert.setDate(5, new java.sql.Date(System.currentTimeMillis()));
	        psInsert.setFloat(6, tour.getSaleOff());
	        psInsert.setInt(7, tour.getBookedPeople());
	        int rowsInserted = psInsert.executeUpdate();

	        PreparedStatement psUpdate = con.prepareStatement(sqlUpdateTour);
	        psUpdate.setDate(1, java.sql.Date.valueOf(tour.getStartDate()));
	        psUpdate.setDate(2, java.sql.Date.valueOf(tour.getEndDate()));
	        psUpdate.setInt(3, tour.getId());
	        int rowsUpdated = psUpdate.executeUpdate();

	        if (rowsInserted > 0 && rowsUpdated > 0) {
	            con.commit();
	            check = true;
	        } else {
	            con.rollback(); // Nếu một trong hai thất bại thì rollback
	        }

	        psInsert.close();
	        psUpdate.close();
	    } catch (Exception e) {
	        try {
	            con.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            con.setAutoCommit(true); // Đừng quên bật lại autoCommit
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return check;
	}


}
