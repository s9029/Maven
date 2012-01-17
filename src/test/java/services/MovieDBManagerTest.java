package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import services.MovieDBManager;
import sala.patryk.projekt.wypozyczalniavideo.*;

public class MovieDBManagerTest {

	MovieDBManager MovieManager = new MovieDBManager();
	@Before
	public void setUp() throws Exception {
		MovieManager.addMovie(new Movie("title", (float)100));
	}

	@After
	public void tearDown() throws Exception {
		MovieManager.deleteAllMovies();
	}

	@Test
	public void testAddMovie() {
		MovieManager.addMovie(new Movie("title2", (float)100));
		assertEquals(2, MovieManager.getAllMovies().size());
	}

	@Test
	public void testGetAllMovies() {
		assertEquals(1, MovieManager.getAllMovies().size());
	}

	@Test
	public void testDeleteAllMovies() {
		MovieManager.deleteAllMovies();
		assertTrue(MovieManager.getAllMovies().isEmpty());
	}

	@Test
	public void testFindMovieByTitle() {
		MovieManager.addMovie(new Movie("title", (float)150));
		assertEquals(2, MovieManager.findMovieByTitle("title").size());
	}

	@Test
	public void testDeleteMovie() {
		MovieManager.addMovie(new Movie("title2", (float)150));
		MovieManager.deleteMovie(MovieManager.findMovieByTitle("title"));
		assertEquals(1, MovieManager.getAllMovies().size());
	}

}
