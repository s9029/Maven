package blazej.kwidzinski.mpr.wypozyczalniaaut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import services.AutoDBManager;

public class CarRental {

	private float totalCashInCarRental = 0;

	private static Logger logger = Logger.getLogger(CarRental.class);

	private List<Auto> autoList;
	
	private AutoDBManager autoDbManager;
	public CarRental() {
		autoList = new ArrayList<Auto>();
		logger.debug("Zrobiono nowa wypozyczalnie samochodow");
	}

	public void removeAutoFromCarRental(Auto auto) {
		autoList.remove(auto);
		logger.debug("Usunieto samochod z wypozyczalni: " + auto.getModel());
	}
	
	public void setNewPriceForAuto(String autoModel, float newPrice){
		Auto auto = findAutoByModel(autoModel);
		auto.setPrice(newPrice);
	}

	public void addNewAuto(Auto auto) {
		autoList.add(auto);
		logger.debug("dodano nowy samochod do wypozyczalni: " + auto.getModel());
	}

	public void printAllCars() {
		logger.info("Lista wszystkich samochodow w wypozyczalni");
		for (Auto auto : autoList) {
			logger.info(auto.toString());
		}
	}


	public Auto findAutoByModel(String model) {
		for (Auto auto : autoList) {
			if (auto.getModel().equals(model)) {
				return auto;
			}
		}
		return null;
	}

}
