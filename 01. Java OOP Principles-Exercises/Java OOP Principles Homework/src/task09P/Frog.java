package task09P;

public class Frog extends Animals {

    public Frog(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "Frogggg";
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n")
                .append(super.toString());

        return sb.toString() ;
    }
}
