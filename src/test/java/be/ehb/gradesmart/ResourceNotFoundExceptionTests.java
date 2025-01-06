package be.ehb.gradesmart;
import static org.junit.jupiter.api.Assertions.*;

import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTests {

    @Test
    public void testResourceNotFoundException_Message() {
        String errorMessage = "Resource not found!";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "The exception message should match the provided message.");
    }

    @Test
    public void testResourceNotFoundException_DefaultMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        assertNull(exception.getMessage(), "The exception message should be null when no message is provided.");
    }
}
