package blazej.kwidzinski.mpr.wypozyczalniaaut;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

import blazej.kwidzinski.mpr.wypozyczalniaaut.*;

import services.*;

public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);
	
	
	public static void main(String[] args) {
	

	CustomerDBManager CustomerManager = new CustomerDBManager();
	
	try{
		CustomerManager.addCustomer(new Customer("Jan Kowalski", 1000));
		CustomerManager.addCustomer(new Customer("Jan Nowak", 2000));
		CustomerManager.addCustomer(new Customer("Jan Adamczyk", 1500));
	}
	catch(InvalidMoneyAmountValue e){
		e.getMessage();
	}
	
	
	System.out.println("Show Customers_");
	for (Customer customer : CustomerManager.getAllCustomers()) {
		System.out.println("Name= " + customer.getName());
	}

	AutoDBManager AutoManager = new AutoDBManager();

	AutoManager.addAuto(new Auto("Opel Astra", 500));
	AutoManager.addAuto(new Auto("Ford Focus", 600));
	AutoManager.addAuto(new Auto("Ford Focus", 650));
	AutoManager.addAuto(new Auto("Ford Focus", 700));

	System.out.println("Show Cars_");
	for (Auto auto : AutoManager.getAllAuto()) {
		System.out.println("Car model: " + auto.getModel() + "\nPrice =  "
				+ auto.getPrice());
	}

	// DB Manager segment

	HistoryDBManager HistoryManager = new HistoryDBManager();
	List<Integer> CustomerID = CustomerManager.findCustomerByName("Jan Kowalski");
	List<Integer> AutoID = AutoManager.findAutoByModel("Ford Focus");
	
	for(Integer ID : CustomerID ){
		System.out.println("Customer_ " + ID);
	}
	for(Integer ID : AutoID ){
		System.out.println("Auto Model_ " + ID);
	}
	
	HistoryManager.SellAuto(CustomerID, AutoID);
	
	for (Auto auto : HistoryManager.getHistory(CustomerManager.findCustomerByName("Jan"))) {
		System.out.println("Model: " + auto.getModel() + "\nPrice: " + auto.getPrice());
	}
	
	// clean database segment
	HistoryManager.deleteAllSales();
	AutoManager.deleteAllCars();
	CustomerManager.deleteAllCustomers();
	
	}
}
