package task09P;

public class Tomcats extends Cat {

    private static final String GENDER="male";
    public Tomcats(String name, int age) {
        super(name, age, GENDER);
    }

    @Override
    public String produceSound() {
        return "Give me one million b***h";
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n")
                .append(super.toString());

        return sb.toString() ;
    }
}
