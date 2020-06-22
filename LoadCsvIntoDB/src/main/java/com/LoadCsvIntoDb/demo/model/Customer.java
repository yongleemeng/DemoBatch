package com.LoadCsvIntoDb.demo.model;

public class Customer {
  //@Id
  //@Column(name="customer_id", nullable = false)
  //@GeneratedValue(strategy=GenerationType.AUTO)
  private String id;

  //@Column(name="first_name", nullable = false)
  private String firstName;

  //@Column(name="last_name", nullable = false)
  private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append("]");
		return builder.toString();
	}

}
