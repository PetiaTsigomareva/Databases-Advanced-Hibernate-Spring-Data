package task03;

public class Ferrari implements Car {
    private String model;
    private String driver;
    private static final String MODEL = "488-Spider";

    Ferrari( String driver){

        this.model= MODEL;
        this.driver=driver;
    }

    @Override
    public String useBrakes() {
        return "Brakes!";
    }

    @Override
    public String pushTheGasPedal() {
        return "Zadu6avam sA!";
    }



    @Override
    public String toString() {
       // return this.model + '/' + useBrakes()+"/"+ pushTheGasPedal()+"/" + this.driver;
        return (String.format("%s/%s/%s/%s", this.model, this.useBrakes(), this.pushTheGasPedal(), this.driver));
    }
}
