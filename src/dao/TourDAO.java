package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.*;

// thư viện  sử lý thời gian :
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Tour;
import model.User;

public class TourDAO extends DAO {

	public TourDAO() {
		super();
	}

	public static LocalDate parseFlexibleDate(String input) {
		String[] parts = input.split("/");

		// Lấy ngày hiện tại
		LocalDate now = LocalDate.now();

		// Làm chuẩn dữ liệu từng phần
		String day = String.valueOf(now.getDayOfMonth()), month = String.valueOf(now.getMonthValue()), year = String.valueOf(now.getYear());
		
		try {
			if (parts.length >= 1) {
				day = String.format("%02d", Integer.parseInt(parts[0]));
			}
			if (parts.length >= 2) {
				month = String.format("%02d", Integer.parseInt(parts[1]));
			}
			if (parts.length == 3) {
				year = parts[2].length() == 2 ? "20" + parts[2] : parts[2]; // xử lý năm 2 chữ số
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid input: non-numeric value.");
		}

		String fullDate = String.format("%s/%s/%s", day, month, year);

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return LocalDate.parse(fullDate, formatter);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid date format: " + fullDate);
		}
	}

	public ArrayList<Tour> searchFreeTour(String departurePoint, String destination, String maxCapacity,
			String startDate, String endDate,User user) {
		ArrayList<Tour> result = new ArrayList<Tour>();
		StringBuilder sql = new StringBuilder("SELECT * FROM tour WHERE 1 = 1 ");
		List<Object> params = new ArrayList<>();
		

		boolean check = true; // xét các trường hợp 1 cái sai vẫn sort các trường hợp còn lại
		String descripton; // lưu và hiển thị các lỗi của người dùng 
		LocalDate start = null, end = null; // check ngày sau phải nhỏ hơn ngày sau :
		

//		xử lý đầu vào 

		if (departurePoint != null && !departurePoint.isEmpty()) {
			sql.append("AND departurePoint = ? ");
			params.add(departurePoint);
		}

		if (destination != null && !destination.isEmpty()) {
			sql.append("AND destination = ? ");
			params.add(destination);
		}

		if (maxCapacity != null && !maxCapacity.trim().isEmpty()) {
			try {
				int maxCap = Integer.parseInt(maxCapacity);
				sql.append("AND maxCapacity >= ? ");
				params.add(maxCap);
			} catch (NumberFormatException e) {
				System.err.println("maxCapacity không phải là số hợp lệ: " + maxCapacity);
				descripton = "maxCapacity không phải là số hợp lệ: " + maxCapacity;
				check = false;
			}
		}
		
		
		if(startDate != null && !startDate.isEmpty()) {
			try {
				sql.append("AND(endDate < ? OR startDate IS NULL)");
				start= parseFlexibleDate(startDate);
				params.add(start);
				System.err.println("Ngày bắt đầu : " + start + " ");
			}catch (Exception e) {
				// TODO: handle exception
				check = false;
				System.err.println("Ngày bắt đầu không hợp lệ");
				descripton = "Ngày bắt đầu không hợp lệ";
			}
			
		}
		
		if(endDate != null && !endDate.isEmpty()) {
			try {
				end= parseFlexibleDate(endDate);
				System.err.println("Ngày kết thúc : " + end + " ");
			}catch (Exception e) {
				// TODO: handle exception
				System.err.print("Ngày kết thúc không hợp lệ");
				descripton = "Ngày kết thúc không hợp lệ";
				check = false;
			}
			
		}
		
		// kiểm tra ngày tháng có hợp lệ không
		if(start != null && end != null && end.isBefore(start)) {
			check = false;
			descripton = "Ngày bắt đầu lớn hơn ngày kết thúc";
			System.err.print("Ngày bắt đầu lớn hơn ngày kết thúc");
		}
		
		if(start == null || end == null) {
			check = false;
			descripton = "Thiếu ngày bắt đầu hoặc kết thúc";
			System.err.print("Thiếu ngày bắt đầu hoặc kết thúc");
		}

//		xử lý logic 
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i)); // JDBC index bắt đầu từ 1
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next() && check) {
				Tour tour = new Tour();
				tour.setId(rs.getInt("id"));
				tour.setNameTour(rs.getNString("name"));
				tour.setDepaturePoint(rs.getNString("departurePoint"));
				tour.setDestination(rs.getNString("destination"));
				tour.setSaleOff(rs.getFloat("saleOff"));
				tour.setMaxCapacity(rs.getInt("maxCapacity"));
				tour.setPrice(rs.getFloat("price"));
				tour.setUser(user);
				tour.setStartDate(start);
				tour.setEndDate(end);
				result.add(tour);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
