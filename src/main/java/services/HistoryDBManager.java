package services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import blazej.kwidzinski.mpr.wypozyczalniaaut.*;


public class HistoryDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement SellAutoStmt;
	private PreparedStatement deleteAllSalesStmt;
	private PreparedStatement deleteHistoryStmt;
	private PreparedStatement getHistoryStmt;

	public HistoryDBManager() {
		try {
			Properties props = new Properties();

			try {
				props.load(ClassLoader
						.getSystemResourceAsStream("mydb.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(props.getProperty("url"));

			stmt = conn.createStatement();
			boolean HistoryTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while (rs.next()) {
				if ("History".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					HistoryTableExists = true;
					break;
				}
			}

			if (!HistoryTableExists) {
				stmt.executeUpdate("CREATE TABLE history(customerID int, carID bigint, CONSTRAINT customer_id_fk FOREIGN KEY (customerID) REFERENCES customer (id),"
						+ " CONSTRAINT car_id_fk FOREIGN KEY (carID) REFERENCES cars (id))");
			}

			SellAutoStmt = conn
					.prepareStatement("INSERT INTO history (customerID, carID) VALUES (?, ?)");

			deleteHistoryStmt = conn
					.prepareStatement("DELETE FROM history WHERE customerID = ?");

			deleteAllSalesStmt = conn
					.prepareStatement("DELETE FROM history");

			getHistoryStmt = conn
					.prepareStatement("SELECT cars.model, cars.price FROM cars,"
							+ " history WHERE customerID = ? and carID = cars.id");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void SellAuto(List<Integer> myCustomerList, List<Integer> myCarList) {
		try {
			for (Integer customerID : myCustomerList) {
				for (Integer carID : myCarList) {
					SellAutoStmt.setInt(1, customerID);
					SellAutoStmt.setInt(2, carID);
					SellAutoStmt.executeUpdate();
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void deleteHistory(List<Integer> myCustomerList) {
		try {
			for (Integer customerID : myCustomerList) {
				deleteHistoryStmt.setInt(1, customerID);
				deleteHistoryStmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void deleteAllSales() {
		try {
			deleteAllSalesStmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Auto> getHistory(List<Integer> myCustomerList) {
		List<Auto> myCarList = new ArrayList<Auto>();
		try {
			for (Integer customerID : myCustomerList) {
				getHistoryStmt.setInt(1, customerID);
				ResultSet rs = getHistoryStmt.executeQuery();
				while (rs.next()) {
					myCarList.add(new Auto(rs.getString("model"), rs
							.getFloat("price")));
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return myCarList;
	}

}