package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Client;


public class ClientDAO  extends DAO {
	public ClientDAO() {
		super();
	}
	
	public ArrayList<Client> searchClient(String name, String email, String phoneNumber) {
		ArrayList<Client> result = new ArrayList<Client>();
		StringBuilder sql = new StringBuilder("SELECT * FROM client WHERE 1 = 1 ");
		List<Object> params = new ArrayList<>();
		

		String descripton; // lưu và hiển thị các lỗi của người dùng 
		

//		xử lý đầu vào 
		if (name != null && !name.isEmpty()) {
			sql.append("AND name = ? ");
			params.add(name);
		}

		if (email != null && !email.isEmpty()) {
			sql.append("AND email = ? ");
			params.add(email);
		}

		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			sql.append("AND phoneNumber = ? ");
			params.add(email);
		}
		
//		xử lý logic 
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i)); // JDBC index bắt đầu từ 1
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getNString("name"));
				client.setEmail(rs.getString("email"));
				client.setAddress(rs.getNString("address"));
				client.setPhoneNumber(rs.getString("phoneNumber"));
				result.add(client); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean addClient(String name,String address,String email,String phoneNumber,String note) {
		 String sql = "INSERT INTO client (name, email, phoneNumber, address, note) VALUES (?, ?, ?, ?, ?)";
		 boolean result = false;
		 try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phoneNumber);
            ps.setString(4, address);
            ps.setString(5, note);
            
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
            	result = true;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
