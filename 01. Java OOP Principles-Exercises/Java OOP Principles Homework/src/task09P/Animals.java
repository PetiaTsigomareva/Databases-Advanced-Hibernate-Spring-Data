package task09P;

public class Animals implements ISound {
    public static final String DEFAULT_MESSAGE_FOR_INVALID_INPUT = "Invalid input!";

    private String name;
    private int age;
    private String gender;

    public Animals(String name, int age, String gender) {
        this.setName(name);
        this.setAge(age);
        this.setGender(gender);
    }
    public Animals(String name, int age) {
        this.setName(name);
        this.setAge(age);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(DEFAULT_MESSAGE_FOR_INVALID_INPUT);
        }else {
            this.name = name;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        if (age <= 0) {
            throw new IllegalArgumentException(DEFAULT_MESSAGE_FOR_INVALID_INPUT);
        }else{
            this.age = age;
        }

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException(DEFAULT_MESSAGE_FOR_INVALID_INPUT);
        }else{
            this.gender = gender;
        }

    }

    @Override
    public String produceSound() {
        return "Not implemented!";
    }

    @Override
    public String toString() {

        StringBuilder sb =new StringBuilder();
        sb.append(name).append(" ")
           .append(age).append(" ")
           .append(gender).append(" ");


        return sb.toString();
    }
}
