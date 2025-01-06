package be.ehb.gradesmart;
import static org.junit.jupiter.api.Assertions.*;

import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Test;

public class RessourceAlreadyExistsExceptionTests {

    @Test
    public void testResourceAlreadyExistsException_Message() {
        String errorMessage = "Resource already exists!";
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "The exception message should match the provided message.");
    }

    @Test
    public void testResourceAlreadyExistsException_DefaultMessage() {
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(null);

        assertNull(exception.getMessage(), "The exception message should be null when no message is provided.");
    }
}
