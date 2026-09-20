package backend.entity;

import org.bson.types.ObjectId;

public class User {
	private ObjectId id;
	private String name;
	private String email;
	private Address address;
	public User(String name, String email, Address address) {
		this();
		this.name = name;
		this.email = email;
		this.address = address;
	}
	public User() {
		super();
		id = new ObjectId();
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address adress) {
		this.address = adress;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
	}
	
}
