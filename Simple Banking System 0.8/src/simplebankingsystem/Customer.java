package simplebankingsystem;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {

	protected String name;
	protected String address;
	protected int customerID;
	public Customer(String name, String address, int customerID) {
		super();
		this.setName(name);
		this.setAddress(address);
		this.setCustomerID(customerID);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", address=" + address + ", customerID=" + customerID + "]";
	}
	
}
