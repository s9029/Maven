package services;

import static org.junit.Assert.*;
import sala.patryk.projekt.wypozyczalniavideo.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerDBManagerTest {

	CustomerDBManager CustomerManager = new CustomerDBManager();
	
	@Before
	public void setUp() throws Exception {
		try {
			CustomerManager.addCustomer(new Customer("Pan", (float)1000));
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
			CustomerManager.addCustomer(new Customer("Pan2", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllCustomers() {
		try {
			CustomerManager.addCustomer(new Customer("Pan3", (float)1000));
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
		assertEquals(1,CustomerManager.findCustomerByName("Pan").size());
		
	}

	@Test
	public void testDeleteCustomer() {
		try {
			CustomerManager.addCustomer(new Customer("Pan2", (float)1000));
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
		CustomerManager.deleteCustomer(CustomerManager.findCustomerByName("Pan"));
	}

}
