package task04;

public class Smartphone implements ICalling,IBrowsing{

    public Smartphone(){

    };
    @Override
    public void browse(String site) {
        if (isValidedURL( site)){
            System.out.println("Browsing: " + site +"!");
        }else {
            System.out.println("Invalid URL!");
        }

    }

    @Override
    public void call(String number) {
        if (isValidedPhoneNumber(number)){
            System.out.println("Calling... " + number);
        }else{
            System.out.println("Invalid number!");
        }

    }

    private boolean isValidedPhoneNumber(String phoneNumber ){
        boolean result = true;
       for (int i =0;i<phoneNumber.length();i++){
           String symbol=String.valueOf(phoneNumber.charAt(i));
           if (!isInteger(symbol)){
               result= false;
               break;
           }


       }

        return result;
    }

    private boolean isValidedURL(String url){
        boolean result = true;
        for (int i =0;i<url.length();i++){
            String symbol=String.valueOf(url.charAt(i));
            if (isInteger(symbol)){
                result=false;
                break;
            }


        }

        return result;
    }

    private boolean isInteger(String input)
    {
        boolean result=true;
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException e) {
            result = false;
        }
        // only got here if we didn't return false
        return result;
    }
}
