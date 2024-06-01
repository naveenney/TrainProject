package trainTicketbooking;

public class Passenger {
	String name;
	int age;
	String gender;
	String id;
	String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Passenger(String name, int age, String gender, String id, String status) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Name : " + name + " || Age : " + age + " || gender : " + gender + " || ID : " + id + " || Status : "
				+ status;
	}
}
