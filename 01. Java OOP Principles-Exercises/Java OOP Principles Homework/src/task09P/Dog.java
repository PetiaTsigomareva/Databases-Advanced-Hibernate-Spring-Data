package task09P;

public class Dog extends Animals {

    public Dog(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "BauBau";
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n")
           .append(super.toString());

        return sb.toString() ;
    }
}
