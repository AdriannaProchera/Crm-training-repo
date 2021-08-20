package pl.sda.crm.util;

public class ArgumentValidator {

    public static void validate(boolean condition, String message){
        if(!condition){
            throw new IllegalArgumentException(message);
        }
    }
}
