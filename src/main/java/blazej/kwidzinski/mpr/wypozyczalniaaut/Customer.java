package blazej.kwidzinski.mpr.wypozyczalniaaut;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import services.HistoryDBManager;

public class Customer {
	private long id;
	private Logger logger = Logger.getLogger(Customer.class);
	private final String name;
	private float cash;
	private List<Auto> myAutoList;

	public Customer(String name, float money) throws InvalidMoneyAmountValue {
		myAutoList = new ArrayList<Auto>();
		this.name = name;
		if (money < 0) {
			throw new InvalidMoneyAmountValue();
		}
		this.cash = money;
	}
	
	public Customer(String name, int money) throws InvalidMoneyAmountValue {
		myAutoList = new ArrayList<Auto>();
		this.name = name;
		if (money < 0) {
			throw new InvalidMoneyAmountValue();
		}
		this.cash = (float)money;
	}

	
	public String getName() {
		return name;
	}


	public void showAllMyRentedCars() {
		logger.info("Lista aut klienta o nazwisku: " + name);
		for (Auto auto : myAutoList) {
			logger.info(auto.toString());
		}
	}

	public Auto returnAuto(String model) {
		for (Auto auto : myAutoList) {
			if (auto.getModel().equals(model))
				return auto;
		}
		return null;
	}

	public float payMoney(float priceToPay) throws NoMoneyException {
		if (cash - priceToPay >= 0) { // chce zaplacic jesli mam pieniadze
			cash = cash - priceToPay;
			return priceToPay;
		}
		throw new NoMoneyException(); // ale jak nie mam pieniedzy to nie place
	}



	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name
				+ ", cash=" + cash + ", myCarList=" + myAutoList + "]";
	}


	public float getCash() {
		return cash;
	}


	public void setCash(float cash) {
		this.cash = cash;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

}
