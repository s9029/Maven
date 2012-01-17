package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sala.patryk.projekt.wypozyczalniavideo.*;

public class HistoryDBManagerTest {

	MovieDBManager MovieManager = new MovieDBManager();
	CustomerDBManager CustomerManager = new CustomerDBManager();
	HistoryDBManager HistoryManager = new HistoryDBManager();

	@Before
	public void setUp() throws Exception {
		MovieManager.addMovie(new Movie("title1", (float)100));
		MovieManager.addMovie(new Movie("title2", (float)100));
		MovieManager.addMovie(new Movie("title3", (float)100));

		CustomerManager.addCustomer(new Customer("Pan1", (float)1000));
		CustomerManager.addCustomer(new Customer("Pan2", (float)1000));
		HistoryManager.BorrowMovie(
				CustomerManager.findCustomerByName("Pan1"),
				MovieManager.findMovieByTitle("title2"));
	}

	@After
	public void tearDown() throws Exception {
		HistoryManager.deleteAllLendings();
		MovieManager.deleteAllMovies();
		CustomerManager.deleteAllCustomers();
	}
	
	@Test
	public void testBorrowMovie() {
		MovieManager.addMovie(new Movie("title4",(float)100));
		HistoryManager.BorrowMovie(CustomerManager.findCustomerByName("Pan1"), MovieManager.findMovieByTitle("title4"));
		assertEquals(3, HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan1")));
	}

	@Test
	public void testDeleteCustomerLendings() {
		assertEquals(2, HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan1")).size());
		HistoryManager.deleteMovieLendings(CustomerManager.findCustomerByName("Pan1"));
		assertEquals(0, HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan1")).size());
	}

	@Test
	public void testDeleteAllLendings() {
		HistoryManager.deleteAllLendings();
		assertTrue(HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan1")).isEmpty());
	}

	@Test
	public void testGetCustomerMovie() {
		assertEquals(2, HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan1")).size());
	}

}
