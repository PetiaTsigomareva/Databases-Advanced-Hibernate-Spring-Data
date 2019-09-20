package task06;

public class Participant {

    private String id;
    private String name;
    private String birthdate;

    public Participant(String id) {

        this.id = id;
    }

    public Participant(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.birthdate = date;
    }

    public Participant(String name, String birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public String getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }
}
