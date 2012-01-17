package sala.patryk.projekt.wypozyczalniavideo;

public class Movie {

	private String title;
	private float price;

	public Movie(String title, float price) {
		this.title = title;
		this.price = price;
	}
	
	public Movie(String title, int price) {
		this.title = title;
		this.price = (float)price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}


}
