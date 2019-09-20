package task06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String [] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input=reader.readLine();
        List<Participant> participantList = new LinkedList<>();
        while (!input.equals("End")){

            String [] inputParts= input.split("\\s+");
            addToSociety(inputParts, participantList);
            input=reader.readLine();
        }

        String year=reader.readLine();
        printBirthdayWithSameYear(participantList,year);


    }

    private static void addToSociety(String [] input  , List<Participant> list){
        Participant participant =null;
        String id;
        String date;
        String name;
       switch (input[0]){

           case "Robot":
               String model=input[1];
               id=input[2];
               participant =new Robot(id,model);
               break;
           case "Citizen":
               name = input[1];
               int age = Integer.parseInt(input[2]);
               id=input[3];
               date=input[4];
               participant =new Citizen(id,name,date,age);
               break;
           case "Pet":
               name=input[1];
               date=input[2];
               participant=new Pet(name,date);

                   break;
           default:
           break;
       }

       list.add(participant);

    }

    private static void printBirthdayWithSameYear(List<Participant> list, String year){
        list.stream()
             .filter(e->e.getBirthdate()!= null && e.getBirthdate().endsWith(year))
             .forEach(d->System.out.println(d.getBirthdate()));
    }
}
