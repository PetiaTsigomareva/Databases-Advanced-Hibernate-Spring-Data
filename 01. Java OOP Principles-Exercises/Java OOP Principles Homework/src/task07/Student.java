package task07;

public class Student extends Human {
      private String facultyNumber;
    public Student(String firstName, String lastName, String facNumber) {
        super(firstName, lastName);

        setFacultyNumber(facNumber);
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        if (!isValidNumber(facultyNumber)){
            throw new IllegalArgumentException("Invalid faculty number!");
        }else {
            this.facultyNumber = facultyNumber;
        }
    }

    private boolean isValidNumber(String number){

        boolean result= false;

        if (number.length()>=5 && number.length()<=10){

            result=true;
        }

        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb=new StringBuilder();
        sb.append(super.toString())
                .append(String.format("Faculty number: %s%n",  facultyNumber));
        return sb.toString();
    }
}
