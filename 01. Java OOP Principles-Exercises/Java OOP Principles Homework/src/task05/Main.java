package task05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String [] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input=reader.readLine();
        List<Society> societyList = new LinkedList<>();
        while (!input.equals("End")){

            String [] inputParts= input.split("\\s+");
            addToSociety(inputParts,societyList);
            input=reader.readLine();
        }

        String fakeId=reader.readLine();
        printDetainedId(societyList,fakeId);


    }

    private static void addToSociety(String [] input  , List<Society> list){
        Society society=null;
        String id;
       switch (input.length){

           case 2:
               String model=input[0];
               id=input[1];
               society=new Robot(id,model);
               break;
           case 3:
               String name = input[0];
               int age = Integer.parseInt(input[1]);
               id=input[2];
               society =new Citizen(id,name,age);
               break;
           default:
           break;
       }

       list.add(society);

    }

    private static void printDetainedId(List<Society> list, String id){
        list.stream()
             .filter(e->e.getId().endsWith(id))
             .forEach(d->System.out.println(d.getId()));
    }
}
