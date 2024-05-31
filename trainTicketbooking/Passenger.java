package trainTicketbooking;


public class Passenger {
    String name;
    int age;
    String gender;
    String id;
    String status;

    public Passenger(String name, int age, String gender, String id, String status) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Name : " + name + " || Age : " + age + " || gender : " + gender + " || ID : " + id + " || Status : " + status;
    }
}
