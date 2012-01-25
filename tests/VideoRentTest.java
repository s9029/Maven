package blazej.kwidzinski.mpr.wypozyczalniaaut.tests;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import blazej.kwidzinski.mpr.wypozyczalniaaut.Customer;
import blazej.kwidzinski.mpr.wypozyczalniaaut.History;
import blazej.kwidzinski.mpr.wypozyczalniaaut.InvalidMoneyAmountValue;
import blazej.kwidzinski.mpr.wypozyczalniaaut.ItemType;
import blazej.kwidzinski.mpr.wypozyczalniaaut.Auto;
import blazej.kwidzinski.mpr.wypozyczalniaaut.NoMoneyException;
import blazej.kwidzinski.mpr.wypozyczalniaaut.AutoRental;
import services.CustomerDBManager;
import services.HistoryDBManager;
import services.AutoDBManager;

public class AutoRentTest {

	private static final Auto astra = new Auto("Opel Astra", ItemType.Hatchback,
			"R. Scott", 300);
	private static final Auto mondeo = new Auto("Ford Mondeo", ItemType.Kombi,
			"Peyo", 600);
	private static final Auto fiesta = new Auto(
			"Ford Fiesta", ItemType.Hatchback, "Jan Nowak",
			500);
	private static final Auto galaxy = new Auto(
			"Ford Galaxy", ItemType.VAN, "Jan Nowak", 750);
	private static final Auto golf = new Auto("Volkswagen Golf",
			ItemType.Kombi, "Jan Nowak", 9.99F);
	private AutoRental autoRental;

	@Before
	public void setUp() throws Exception {
		autoRental = new AutoRental();
		autoRental.addNewAuto(astra);
		autoRental.addNewAuto(mondeo);
		autoRental.addNewAuto(fiesta);
		autoRental.addNewAuto(galaxy);
		autoRental.addNewAuto(golf);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void showAllCarsInAutoRentalTest() {
		autoRental.printAllCars();
	}

	@Test
	public void removeAutoFromAutoRentalTest() {
		Auto smerfy = autoRental.findAutoByTitle("Ford Mondeo");
		autoRental.printAllCars();
		autoRental.removeAutoFromAutoRental(mondeo);
		autoRental.printAllCars();
	}

	@Test
	public void historyTableTest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		Customer customer = null;
		CustomerDBManager customerDBManager = new CustomerDBManager();
		AutoDBManager autoDBManager = new AutoDBManager();
		try {
			customer = new Customer("Adam Bednarczyk TESTER", 15500);
			customerDBManager.addCustomer(customer);
			customer = new Customer("Filip Olcha TESTER", 1420);
			customerDBManager.addCustomer(customer);

			autoDBManager.createNewAuto(fiesta);

			autoDBManager.createNewAuto(new Auto(
					"Volkswagen Passat", ItemType.Kombi,
					"Jan Nowak", 500));
		} catch (InvalidMoneyAmountValue e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// znajdz dwoch ostatnich klietow w bazie
		List<Customer> allcustomers = customerDBManager.getAllcustomers();
		Customer customer1 = allcustomers.get(allcustomers.size() - 1);
		Customer customer2 = allcustomers.get(allcustomers.size() - 2);
		// znajdz dwa ostatnie samochody w bazie
		List<Auto> findAllCars = new AutoDBManager().findAllCars();
		Auto auto1 = findAllCars.get(findAllCars.size() - 1);
		Auto auto2 = findAllCars.get(findAllCars.size() - 2);

		// wypozycz samochody
		customer1.takeAutoAndCreateHistoryLog(auto1);
		customer1.takeAutoAndCreateHistoryLog(auto2);

		customer2.takeAutoAndCreateHistoryLog(auto2);
		customer2.takeAutoAndCreateHistoryLog(auto1);
		customer2.takeAutoAndCreateHistoryLog(auto2);

		// pokaz historie dla klienta
		customer1.showHistory();
		customer2.showHistory();

	}

	@Test
	public void manyToManyRelationTest() {
		System.out
				.println("\n\n\n********************** TEST LACZENIA WIELE DO WIELU NA PRZYKLADZIE TABELKI HISTORY **********************\n");
		try {
			HistoryDBManager historyDBManager = new HistoryDBManager();
			AutoDBManager autoDBManager = new AutoDBManager();
			CustomerDBManager customerDBManager = new CustomerDBManager();

			Customer customer = new Customer("Adam Kowalski", 600);
			customerDBManager.addCustomer(customer);
			Customer customerSAVEDinDB = customerDBManager
					.findCustomerByName("Adam Kowalski");

			historyDBManager.deleteHistoryForCustomer(customerSAVEDinDB);
			// clear MOVIES table

			int deleteAllCarsStatus = autoDBManager.deleteAllCars();
			Assert.assertTrue(
					"Blad, nie mozna usunac wszystkich samochodow z tabeli MOVIE, STATUS="
							+ deleteAllCarsStatus, deleteAllCarsStatus == 1);
			// add cars to DB
			autoDBManager.createNewAuto(mondeo);
			autoDBManager.createNewAuto(fiesta);
			autoDBManager.createNewAuto(astra);
			autoDBManager.createNewAuto(golf);
			autoDBManager.createNewAuto(galaxy);

			List<Auto> allCarsInDatabase = autoDBManager.findAllCars();

			// wypozyczymy samochod kilka razy i zweryfikujemy czy jest to
			// odnotowane w historii
			for (Auto auto : allCarsInDatabase) {
				auto.setAvailable(true);
				autoRental.rentAuto(customerSAVEDinDB, auto);
			}

			int numberOfRentedCars = allCarsInDatabase.size();

			customerSAVEDinDB.showHistory();

			List<History> historyForCustomer = historyDBManager
					.getHistoryForCustomer(customerSAVEDinDB);

			Assert.assertNotNull("BLAD ! brak historii dla kienta",
					historyDBManager);
			Assert.assertTrue(
					" BLAD! ilosc wpisow w tabelce HISTORY nie zgadza sie z iloscia wypozyczonych samochodow",
					historyForCustomer.size() == numberOfRentedCars);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMoneyAmountValue e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoMoneyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println("\n\n\n********************** KONIEC **********************\n");
	}

	@Test
	public void testTRANSAKCJI() {
		// DBMANAGER

		Customer c = null;
		Customer c2 = null;
		try {
			c = new Customer("Rafal XYZ", 200);
			c2 = new Customer("Paweł ZYX", 100);
		} catch (InvalidMoneyAmountValue e) {
			e.printStackTrace();
		}
		try {

			CustomerDBManager db = new CustomerDBManager();

			AutoDBManager mdb = new AutoDBManager();

			// TRANSACTIONS

			int beforeTransaction = db.getAllcustomers().size();

			db.startTransaction();
			db.addCustomer(c);
			db.addCustomer(c2);
			// nic nie jest jeszcze zapisane w DB

			db.rolbackTransaction(); // wycofanie transackcji

			int afterTransactionRolback = db.getAllcustomers().size();
			Assert.assertTrue("Wynik przed transakcja i po jest rozny, BLAD!",
					beforeTransaction == afterTransactionRolback);

			List<Customer> allcustomers = db.getAllcustomers();

			System.out.println("Lista Klientow po wycofaniu transakcji");
			for (Customer customer : allcustomers) {
				System.out.println(customer);
			}
			beforeTransaction = db.getAllcustomers().size();
			db.startTransaction();

			db.addCustomer(c);
			db.addCustomer(c2);

			db.commitTransaction();

			allcustomers = db.getAllcustomers();

			afterTransactionRolback = db.getAllcustomers().size();
			Assert.assertTrue(
					"Wynik przed transakcja i po jest TAKI SAM, BLAD!",
					beforeTransaction < afterTransactionRolback);

			System.out.println("Lista Klientow po zakomitowaniu transakcji");
			for (Customer customer : allcustomers) {
				System.out.println(customer);
			}

			Customer customer = allcustomers.get(0);

			mdb.addAutoToCustomer(customer, gladiator);
			List<Auto> cars = mdb.findAllCars();

			for (Auto auto : cars) {
				System.out.println(auto);
			}

			for (Customer cc : db.getAllcustomers()) {
				System.out.println(cc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void rentAutoTest() {
		Customer customer = null;
		try {
			customer = new Customer("Mariusz Wlazly", 200);
		} catch (InvalidMoneyAmountValue e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Auto mondeoAuto = autoRental.findAutoByModel("Ford Mondeo");

		try {
			autoRental.rentAuto(customer, mondeoAuto);
			System.out
					.println("Sprawdzanie czy wypozyczony samochod ma FLAGE AVAILABLE=FALSE");
			boolean available = mondeoAuto.isAvailable();
			Assert.assertFalse(
					"BLAD! samochod mimo wypozyczenia ma flage dostepnosci = true",
					available);
			System.out.println("Udalo sie wypozyczyc samochod "
					+ houseAuto.getMondeo());
		} catch (NoMoneyException e) {
			e.printStackTrace();
			Assert.fail("Wystapil wyjatek w czasie proby wypozyczenia samochodu: "
					+ e.getMessage());
		}

	}

	@Test
	public void settingPriceTest() {
		float newPrice = 555;
		autoRental.setNewPriceForAuto("Ford Mondeo", newPrice);
		Auto auto = autoRental.findAutoByModel("Ford Mondeo");
		Assert.assertEquals(
				"BLAD, Nie udalo sie ustawic nowej ceny dla samochodu ",
				auto.getPrice(), newPrice);

	}

	@Test
	public void findByAutoTypeTest() {
		System.out.println("Wyszukiwanie po rodzaju samochodu...");
		List<Auto> resultList = autoRental.findAutoByType("VAN");
		Assert.assertNotNull(
				"Blad, lista zwrocona z metody wyszukujacej po rodzaju samochodu zwrocila NULL",
				resultList);
		Assert.assertTrue(
				"Blad, lista zwrocona z metody wyszukujacej po rodzaju samochodu jest pusta",
				resultList.size() > 0);
		if (resultList.size() > 0) {
			for (int i = 0; i < resultList.size(); i++) {
				System.out
						.println("Znaleziono " + resultList.get(i).toString());
			}
		}
	}

	@Test
	public void findByModelTest() {
		Auto smerfy = autoRental.findAutoByModel("Mondeo");
		Assert.assertNotNull(
				"Blad, nie znaleziono samochodu Mondeo szukajac po modelu", mondeo);
		if (mondeo != null)
			System.out.println("Znaleziono samochod Mondel wyszukujac po modelu");
	}

	@Test(expected = InvalidMoneyAmountValue.class)
	public void createCustomerWithNegativeAmountOfMoneyTest()
			throws InvalidMoneyAmountValue {
		System.out.println("Test wyjatku InvalidMoneyAmountValue ");
		Customer customer1 = new Customer("Jan Nowak", -200);
	}

	@Test(expected = NoMoneyException.class)
	public void clientHasNoMoneyToPayTest() throws InvalidMoneyAmountValue,
			NoMoneyException {
		System.out.println("Test wyjatku NoMoneyException ");
		Customer customer1 = new Customer("Jan Nowak", 1);
		Auto mondeo = autoRental.findAutoByTitle("Mondeo");
		Assert.assertNotNull("nie znaleziono samochodu Mondeo", mondeo);
		System.out.println("Znaleziono samochod Mondeo");
		autoRental.rentAuto(customer1, mondeo);
	}
}
