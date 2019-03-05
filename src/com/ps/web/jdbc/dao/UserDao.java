package com.ps.web.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.ps.web.jdbc.model.EmailAccount;
import com.ps.web.jdbc.model.User;

public class UserDao {
	private DataSource datasource;

	public UserDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public EmailAccount getEmailAccountInf() throws Exception {
		EmailAccount ema = new EmailAccount();
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement("select * from emaccount where id = 1");
			rs = statement.executeQuery();

			while (rs.next()) {
				ema.setAddress(rs.getString("address"));
				ema.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, rs);
		}
		return ema;
	}

	public List<User> showListOfUsers() throws Exception {
		List<User> userList = new ArrayList<>();
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			conn = datasource.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery("select * from users");

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));

				userList.add(user);
			}

			return userList;

		} finally {
			close(conn, statement, rs);
		}
	}

	public void addNewUser(User user) throws Exception {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement("insert into users(firstName, lastName, email) values(?,?,?)");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());

			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, null);
		}

	}

	public Boolean removeItem(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		Boolean st = false;
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement("delete from users where id=?");
			statement.setLong(1, id);
			st = statement.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, null);
		}
		return st;
	}

	public Boolean updateUserInfo(User user) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		Boolean st = false;
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement("update users set firstName = ?, lastName = ?, email = ? where id = ?");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setLong(4, user.getId());
			st = statement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, null);
		}
		return st;
	}

	public User getUser(Long id) throws SQLException {
		User user = new User();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement("select * from users where id = ?");
			statement.setLong(1, id);
			rs = statement.executeQuery();

			while (rs.next()) {
				user.setId(id);
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, rs);
		}
		return user;
	}

	public Map<String, String> getNamesEmailsFromDb(String recipients) throws SQLException {
		Map<String, String> mapRecipents = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement(String.format("select * from users where id in (%s)", recipients));
			rs = statement.executeQuery();

			while (rs.next()) {
				mapRecipents.put(rs.getString("email"), rs.getString("firstName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, rs);
		}
		return mapRecipents;
	}

	private void close(Connection conn, Statement statement, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (conn != null) {
			conn.close();
		}
	}
}
