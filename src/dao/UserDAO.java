package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDAO extends DAO{
	
	public UserDAO() {
		super();
	}
	
	public boolean checkLogin(User user) {
		boolean result = false;
		String sql = "SELECT username,address,email,phoneNumber,role FROM user WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user.setAddress(rs.getNString("address"));
				user.setEmail(rs.getNString("email"));
				user.setPhoneNumber(rs.getNString("phoneNumber"));
				user.setRole(rs.getNString("role"));
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
