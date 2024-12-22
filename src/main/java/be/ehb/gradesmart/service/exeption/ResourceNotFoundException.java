package be.ehb.gradesmart.service.exeption;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);

    }
}
