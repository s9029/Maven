package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import blazej.kwidzinski.mpr.wypozyczalniaaut.*;

import services.AutoDBManager;

public class AutoDBManagerTest {

	AutoDBManager AutoManager = new AutoDBManager();
	@Before
	public void setUp() throws Exception {
		AutoManager.addAuto(new Auto("model", (float)100));
	}

	@After
	public void tearDown() throws Exception {
		AutoManager.deleteAllCars();
	}

	@Test
	public void testAddAuto() {
		AutoManager.addAuto(new Auto("model2", (float)100));
		assertEquals(2, AutoManager.getAllAuto().size());
	}

	@Test
	public void testGetAllCars() {
		assertEquals(1, AutoManager.getAllAuto().size());
	}

	@Test
	public void testDeleteAllCars() {
		AutoManager.deleteAllCars();
		assertTrue(AutoManager.getAllAuto().isEmpty());
	}

	@Test
	public void testFindAutoByModel() {
		AutoManager.addAuto(new Auto("model", (float)150));
		assertEquals(2, AutoManager.findAutoByModel("model").size());
	}

	@Test
	public void testDeleteAuto() {
		AutoManager.addAuto(new Auto("model2", (float)150));
		AutoManager.deleteAuto(AutoManager.findAutoByModel("model"));
		assertEquals(1, AutoManager.getAllAuto().size());
	}

}
