package sala.patryk.projekt.wypozyczalniavideo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import services.MovieDBManager;

public class VideoRental {

	private float totalCashInVideoRental = 0;

	private static Logger logger = Logger.getLogger(VideoRental.class);

	private List<Movie> videoList;
	
	private MovieDBManager movieDbManager;
	public VideoRental() {
		videoList = new ArrayList<Movie>();
		logger.debug("Zrobiono nowa wypozyczalnie video");
	}

	public void removeMovieFromVideoRental(Movie movie) {
		videoList.remove(movie);
		logger.debug("Usunieto film z wypozyczalni: " + movie.getTitle());
	}
	
	public void setNewPriceForMovie(String movieTitle, float newPrice){
		Movie movie = findMovieByTitle(movieTitle);
		movie.setPrice(newPrice);
	}

	public void addNewMovie(Movie movie) {
		videoList.add(movie);
		logger.debug("dodano nowy film do wypozyczalni: " + movie.getTitle());
	}

	public void printAllMovies() {
		logger.info("Lista wszystkich filmow w wypozyczalni");
		for (Movie movie : videoList) {
			logger.info(movie.toString());
		}
	}


	public Movie findMovieByTitle(String title) {
		for (Movie movie : videoList) {
			if (movie.getTitle().equals(title)) {
				return movie;
			}
		}
		return null;
	}

}
