package florinaalexandraanghel.hub.model;

public class Client {
    private String firstName;
    private String lastName;
    //private String accountNumber;
    private Cash cash;


    public Client(String firstName, String lastName, Cash cash){
        this.firstName=firstName;
        this.lastName=lastName;
        this.cash=cash;
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

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }
}
