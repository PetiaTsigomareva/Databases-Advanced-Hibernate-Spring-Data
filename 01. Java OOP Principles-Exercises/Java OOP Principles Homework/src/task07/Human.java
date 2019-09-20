package task07;

public class Human {

    private String firstName;
    private  String lastName;

    public Human(String firstName, String lastName) {
       this.setFirstName(firstName);
       this.setLastName(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
         if(!isValidName(firstName))
         {
             throw new IllegalArgumentException("Expected upper case letter!Argument: firstName");
         }else if(firstName.length()<4){
               throw new IllegalArgumentException("Expected length at least 4 symbols!Argument: firstName");
         }else {
             this.firstName = firstName;
         }

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if(!isValidName(firstName))
        {
            throw new IllegalArgumentException("Expected upper case letter!Argument: lastName");
        }else if(lastName.length()<3){
            throw new IllegalArgumentException("Expected length at least 3 symbols!Argument: lastName");
        }else {
            this.lastName = lastName;
        }

    }

    private boolean isValidName(String name){
        boolean result=false;
        char firstLetter= name.charAt(0);

        if (Character.isUpperCase(firstLetter)){
            result=true;
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("First Name: %s%n",firstName ))
                .append(String.format("Last Name: %s%n",lastName ));

        return sb.toString();
    }
}
