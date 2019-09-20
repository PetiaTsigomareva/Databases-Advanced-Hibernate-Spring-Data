package task06;

public class Citizen extends Participant {
    private int age;

    public Citizen(String id, String name ,String date, int age) {
        super(id,name,date);

        this.age = age;
    }



    public int getAge() {
        return age;
    }
}
