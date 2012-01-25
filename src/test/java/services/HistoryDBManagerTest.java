package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import blazej.kwidzinski.mpr.wypozyczalniaaut.*;


public class HistoryDBManagerTest {

	AutoDBManager AutoManager = new AutoDBManager();
	CustomerDBManager CustomerManager = new CustomerDBManager();
	HistoryDBManager HistoryManager = new HistoryDBManager();

	@Before
	public void setUp() throws Exception {
		AutoManager.addAuto(new Auto("model1", (float)100));
		AutoManager.addAuto(new Auto("model2", (float)100));
		AutoManager.addAuto(new Auto("model3", (float)100));

		CustomerManager.addCustomer(new Customer("Jan1", (float)1000));
		CustomerManager.addCustomer(new Customer("Jan2", (float)1000));
		HistoryManager.SellAuto(
				CustomerManager.findCustomerByName("Jan1"),
				AutoManager.findAutoByModel("model2"));
	}

	@After
	public void tearDown() throws Exception {
		HistoryManager.deleteAllSales();
		AutoManager.deleteAllCars();
		CustomerManager.deleteAllCustomers();
	}
	
	@Test
	public void testSellAuto() {
		AutoManager.addAuto(new Auto("model4",(float)100));
		HistoryManager.SellAuto(CustomerManager.findCustomerByName("Jan1"), AutoManager.findAutoByModel("model4"));
		assertEquals(2, HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan1")).size());
	}

	@Test
	public void testDeleteHistory() {
		assertEquals(1, HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan1")).size());
		HistoryManager.deleteHistory(CustomerManager.findCustomerByName("Jan1"));
		assertEquals(0, HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan1")).size());
	}

	@Test
	public void testDeleteAllSales() {
		HistoryManager.deleteAllSales();
		assertTrue(HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan1")).isEmpty());
	}

	@Test
	public void testGetHistory() {
		assertEquals(1, HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan1")).size());
	}

}
