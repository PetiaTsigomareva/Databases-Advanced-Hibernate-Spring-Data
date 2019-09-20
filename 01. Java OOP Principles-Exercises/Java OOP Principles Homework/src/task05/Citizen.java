package task05;

public class Citizen extends Society {

    private String name;
    private int age;

    public Citizen(String id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
