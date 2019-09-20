package task09P;

public class Kitten extends Cat {
    private static final String GENDER="female";
    public Kitten(String name, int age) {
        super(name, age, GENDER);
    }

    @Override
    public String produceSound() {
        return "Miau";
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n")
                .append(super.toString());

        return sb.toString() ;
    }
}
