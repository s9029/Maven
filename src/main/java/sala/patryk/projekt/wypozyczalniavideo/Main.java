package sala.patryk.projekt.wypozyczalniavideo;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;





import services.CustomerDBManager;
import services.MovieDBManager;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {

		VideoRental videoRental = new VideoRental();

		//movies

		videoRental.printAllMovies();

		try {
			Customer customer1 = new Customer("Pawel Kowalski", -200);

		} catch (InvalidMoneyAmountValue exception) {
			logger.error(exception.getMessage());
		}

		Customer customer2 = null;
		try {
			customer2 = new Customer("Pawel Baranowski", 20);
		} catch (InvalidMoneyAmountValue e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		customer2.showAllMyRentedMovies();

		customer2.showAllMyRentedMovies();

		videoRental.printAllMovies();
		
		
	}
}
