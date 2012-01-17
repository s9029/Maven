package services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import sala.patryk.projekt.wypozyczalniavideo.*;

public class HistoryDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement SellMovieStmt;
	private PreparedStatement deleteAllSalesStmt;
	private PreparedStatement deleteHistoryStmt;
	private PreparedStatement getHistoryStmt;

	public HistoryDBManager() {
		try {
			Properties props = new Properties();

			try {
				props.load(ClassLoader
						.getSystemResourceAsStream("com/pl/reso/jdbc.properties"));
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
				stmt.executeUpdate("CREATE TABLE history(customerID int, movieID bigint, CONSTRAINT customer_id_fk FOREIGN KEY (customerID) REFERENCES customer (id),"
						+ " CONSTRAINT movie_id_fk FOREIGN KEY (movie) REFERENCES movies (id))");
			}

			SellMovieStmt = conn
					.prepareStatement("INSERT INTO history (customerID, movieID) VALUES (?, ?)");

			deleteHistoryStmt = conn
					.prepareStatement("DELETE FROM history WHERE customerID = ?");

			deleteAllSalesStmt = conn
					.prepareStatement("DELETE FROM history");

			getHistoryStmt = conn
					.prepareStatement("SELECT movies.title, movies.price FROM movies,"
							+ " history WHERE customerID = ? and movieID = movies.id");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void SellMovie(List<Integer> myCustomerList, List<Integer> myBookList) {
		try {
			for (Integer customerID : myCustomerList) {
				for (Integer bookID : myBookList) {
					SellMovieStmt.setInt(1, customerID);
					SellMovieStmt.setInt(2, bookID);
					SellMovieStmt.executeUpdate();
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

	public List<Movie> getHistory(List<Integer> myCustomerList) {
		List<Movie> myMovieList = new ArrayList<Movie>();
		try {
			for (Integer customerID : myCustomerList) {
				getHistoryStmt.setInt(1, customerID);
				ResultSet rs = getHistoryStmt.executeQuery();
				while (rs.next()) {
					myMovieList.add(new Movie(rs.getString("title"), rs
							.getFloat("price")));
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return myMovieList;
	}

}