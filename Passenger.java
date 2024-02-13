public class Passenger {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthDate;
    private String passport;

    public Passenger(String firstName, String lastName, String patronymic, String birthDate, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.passport = passport;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }
    
    public String getBirthDate() {
        return birthDate;
    }

}
