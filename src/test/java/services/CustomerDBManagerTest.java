package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import blazej.kwidzinski.mpr.wypozyczalniaaut.*;

public class CustomerDBManagerTest {

	CustomerDBManager CustomerManager = new CustomerDBManager();
	
	@Before
	public void setUp() throws Exception {
		try {
			CustomerManager.addCustomer(new Customer("Jan", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		CustomerManager.deleteAllCustomers();
	}

	@Test
	public void testAddCustomer() {
		try {
			CustomerManager.addCustomer(new Customer("Bogdan", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
		assertEquals(2,CustomerManager.getAllCustomers().size());
	}

	@Test
	public void testGetAllCustomers() {
		try {
			CustomerManager.addCustomer(new Customer("Witold", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
		assertEquals(2,CustomerManager.getAllCustomers().size());
		
	}

	@Test
	public void testDeleteAllCustomers() {
		CustomerManager.deleteAllCustomers();
		assertTrue(CustomerManager.getAllCustomers().isEmpty());
	}

	@Test
	public void testFindCustomerByName() {
		assertEquals(1,CustomerManager.findCustomerByName("Jan").size());
		
	}

	@Test
	public void testDeleteCustomer() {
		try {
			CustomerManager.addCustomer(new Customer("Bogdan", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
		CustomerManager.deleteCustomer(CustomerManager.findCustomerByName("Jan"));
	}

}
