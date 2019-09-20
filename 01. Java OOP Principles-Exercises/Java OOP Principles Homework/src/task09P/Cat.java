package task09P;

public class Cat extends Animals {
    public Cat(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String produceSound() {
        return "MiauMiau";
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n")
                .append(super.toString());

        return sb.toString() ;
    }
}
