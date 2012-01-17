package services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import sala.patryk.projekt.wypozyczalniavideo.*;

public class HistoryDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement LendBookToCustomerStmt;
	private PreparedStatement deleteAllLendingsStmt;
	private PreparedStatement deleteMovieLendingsStmt;
	private PreparedStatement getCustomerBookStmt;

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

			LendBookToCustomerStmt = conn
					.prepareStatement("INSERT INTO history (customerID, movieID) VALUES (?, ?)");

			deleteMovieLendingsStmt = conn
					.prepareStatement("DELETE FROM history WHERE customerID = ?");

			deleteAllLendingsStmt = conn
					.prepareStatement("DELETE FROM history");

			getCustomerBookStmt = conn
					.prepareStatement("SELECT movies.title, movies.price FROM movies,"
							+ " history WHERE customerID = ? and movieID = movies.id");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void BorrowMovie(List<Integer> myCustomerList, List<Integer> myBookList) {
		try {
			for (Integer customerID : myCustomerList) {
				for (Integer bookID : myBookList) {
					LendBookToCustomerStmt.setInt(1, customerID);
					LendBookToCustomerStmt.setInt(2, bookID);
					LendBookToCustomerStmt.executeUpdate();
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void deleteMovieLendings(List<Integer> myCustomerList) {
		try {
			for (Integer customerID : myCustomerList) {
				deleteMovieLendingsStmt.setInt(1, customerID);
				deleteMovieLendingsStmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void deleteAllLendings() {
		try {
			deleteAllLendingsStmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Movie> getHistory(List<Integer> myCustomerList) {
		List<Movie> myMovieList = new ArrayList<Movie>();
		try {
			for (Integer customerID : myCustomerList) {
				getCustomerBookStmt.setInt(1, customerID);
				ResultSet rs = getCustomerBookStmt.executeQuery();
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