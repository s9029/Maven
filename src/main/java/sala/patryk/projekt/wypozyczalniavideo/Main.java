package sala.patryk.projekt.wypozyczalniavideo;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

import sala.patryk.projekt.wypozyczalniavideo.*;
import services.*;

public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);
	
	
	public static void main(String[] args) {
	

	CustomerDBManager CustomerManager = new CustomerDBManager();
	
	try{
		CustomerManager.addCustomer(new Customer("Pan Jan", 10000));
		CustomerManager.addCustomer(new Customer("Pan Mikolaj", 15000));
		CustomerManager.addCustomer(new Customer("Pan Antoni", 15000));
	}
	catch(InvalidMoneyAmountValue e){
		e.getMessage();
	}
	
	
	System.out.println("Show Customers_");
	for (Customer customer : CustomerManager.getAllCustomers()) {
		System.out.println("Name= " + customer.getName());
	}

	MovieDBManager MovieManager = new MovieDBManager();

	MovieManager.addMovie(new Movie("XXX", 100));
	MovieManager.addMovie(new Movie("James Bond", 100));
	MovieManager.addMovie(new Movie("James Bond", 150));
	MovieManager.addMovie(new Movie("James Bond", 200));

	System.out.println("Show Movies_");
	for (Movie movie : MovieManager.getAllMovies()) {
		System.out.println("Movie Title: " + movie.getTitle() + "\nPrice =  "
				+ movie.getPrice());
	}

	// DB Manager segment

	HistoryDBManager HistoryManager = new HistoryDBManager();
	List<Integer> CustomerID = CustomerManager.findCustomerByName("Pan Jan");
	List<Integer> MovieID = MovieManager.findMovieByTitle("James Bond");
	
	for(Integer ID : CustomerID ){
		System.out.println("Customer_ " + ID);
	}
	for(Integer ID : MovieID ){
		System.out.println("Movie Title_ " + ID);
	}
	
	HistoryManager.SellMovie(CustomerID, MovieID);
	
	for (Movie movie : HistoryManager.getHistory(CustomerManager.findCustomerByName("Pan"))) {
		System.out.println("Name: " + movie.getTitle() + "\nPrice: " + movie.getPrice());
	}

	// klasa anonimowe
	
	System.out.println("Imie klienta dluzsze niz 7 znakow");
	CustomerManager.printCustomerWithCondition(
			CustomerManager.getAllCustomers(), new Condition() {
				
				@Override
				public boolean getCondition(Customer customer) {
					return customer.getName().length() > 7;
						
				}
			});
	
	// clean database segmen
//	HistoryManager.deleteAllSales();
//	MovieManager.deleteAllMovies();
//	CustomerManager.deleteAllCustomers();
	
	}
}
