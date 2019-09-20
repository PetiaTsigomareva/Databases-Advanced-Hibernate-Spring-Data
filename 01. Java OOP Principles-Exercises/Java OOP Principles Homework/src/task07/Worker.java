package task07;

public class Worker extends Human{

    private double weekSalary;
    private double workHourPerDay;

    public Worker(String firstName, String lastName, double weekSalary, double workHourPerDay) {
        super(firstName, lastName);
        this.setWeekSalary(weekSalary);
        this.setWorkHourPerDay(workHourPerDay);
    }

    @Override
    public void setLastName(String lastName) {

        if(lastName.length()<3){
            throw new IllegalArgumentException("Expected length at least 3 symbols!Argument: lastName");
        }else {
            super.setLastName(lastName);
        }

    }

    public double getWeekSalary() {
        return weekSalary;
    }

    public void setWeekSalary(double weekSalary) {

        if(weekSalary<10){
            throw new IllegalArgumentException("Expected value mismatch!Argument: weekSalary");
        }else{
            this.weekSalary = weekSalary;
        }

    }

    public double getWorkHourPerDay() {
        return workHourPerDay;
    }

    public void setWorkHourPerDay(double workHourPerDay) {

        if(workHourPerDay<1||workHourPerDay>12){
          throw new IllegalArgumentException("Expected value mismatch!Argument: workHoursPerDay");

        }else {
            this.workHourPerDay = workHourPerDay;
        }
    }

    private double getHourSalary(){
        double result= (this.weekSalary/7)/this.workHourPerDay;

        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb=new StringBuilder();
              sb.append(super.toString())
                .append(String.format("Week Salary: %.2f%n",  weekSalary))
                .append(String.format("Hours per day: %.2f%n", workHourPerDay))
                .append(String.format("Salary per hour: %.2f%n", getHourSalary()));
                return sb.toString();


    }
}
