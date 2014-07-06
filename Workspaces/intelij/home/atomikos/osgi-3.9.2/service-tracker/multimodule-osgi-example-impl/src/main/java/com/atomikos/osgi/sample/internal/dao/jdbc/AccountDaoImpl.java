package com.atomikos.osgi.sample.internal.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import com.atomikos.osgi.sample.internal.dao.AccountDao;

public class AccountDaoImpl implements AccountDao {

	private DataSource dataSource;

	public long getBalance(int accno) throws Exception {
		long res = 0;
		Connection conn = getDataSource().getConnection();

		Statement s = conn.createStatement();
		String query = "select balance from Accounts where account='"
				+ "account" + accno + "'";
		ResultSet rs = s.executeQuery(query);
		if (rs == null || !rs.next())
			throw new Exception("Account not found: " + accno);
		res = rs.getLong(1);
		s.close();
		conn.close();
		return res;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		if (dataSource != null) {
			return dataSource;
		}

		// else :
		throw new RuntimeException(
				"No dataSource available, please come back later...");
	}

	public String getOwner(int accno) throws Exception {
		String res = null;
		Connection conn = getDataSource().getConnection();

		Statement s = conn.createStatement();
		String query = "select owner from Accounts where account='" + "account"
				+ accno + "'";
		ResultSet rs = s.executeQuery(query);
		if (rs == null || !rs.next())
			throw new Exception("Account not found: " + accno);
		res = rs.getString(1);
		s.close();
		conn.close();
		return res;
	}

	public void withdraw(int accno, int amount) throws Exception {

		Connection conn = getDataSource().getConnection();

		Statement s = conn.createStatement();
		String sql = "update Accounts set balance = balance - " + amount
				+ " where account ='account" + accno + "'";
		s.executeUpdate(sql);
		s.close();
		conn.close();

	}
}
