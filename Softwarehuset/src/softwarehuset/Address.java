package softwarehuset;

public class Address {

	private String street, city;
	private int zipcode;
	
	public Address(String street, int zipcode, String city) {
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
	}

}
