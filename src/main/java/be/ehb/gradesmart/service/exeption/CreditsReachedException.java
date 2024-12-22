package be.ehb.gradesmart.service.exeption;

public class CreditsReachedException extends RuntimeException{

    public CreditsReachedException(String message){
        super(message);
    }
}
