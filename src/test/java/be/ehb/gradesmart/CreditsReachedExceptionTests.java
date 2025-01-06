package be.ehb.gradesmart;
import static org.junit.jupiter.api.Assertions.*;



import be.ehb.gradesmart.service.exeption.CreditsReachedException;
import org.junit.jupiter.api.Test;

public class CreditsReachedExceptionTests {

    @Test
    public void testCreditsReachedException_Message() {
        String errorMessage = "The credit limit has been reached!";
        CreditsReachedException exception = new CreditsReachedException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "The exception message should match the provided message.");
    }

    @Test
    public void testCreditsReachedException_DefaultMessage() {
        CreditsReachedException exception = new CreditsReachedException(null);

        assertNull(exception.getMessage(), "The exception message should be null when no message is provided.");
    }
}
