package blazej.kwidzinski.mpr.wypozyczalniaaut;

public class Auto {

	private String model;
	private float price;

	public Auto(String model, float price) {
		this.model = model;
		this.price = price;
	}
	
	public Auto(String model, int price) {
		this.model = model;
		this.price = (float)price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}


}
