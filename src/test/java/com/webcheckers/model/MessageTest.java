package com.webcheckers.model;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * The JUnit test suite for {@link Message}
 *
 * @author Perry Deng 10-23-18
 */
@Tag("Model-tier")
public class MessageTest {
    // a paragraph of lorem
    private String LONG_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
            "ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
            "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
            "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
            "qui officia deserunt mollit anim id est laborum.";


    /**
     * test methods with the null corner case
     */
    @Test
    public void test(){
        Message nullSubject = new Message(null, null);
        assertNull(nullSubject.getText());
        assertNull(nullSubject.getType());
        Message errorSubject = new Message(Message.MessageEnum.error, null);
        assertEquals(Message.MessageEnum.error, errorSubject.getType());
        Message infoSubject = new Message(Message.MessageEnum.info, null);
        assertEquals(Message.MessageEnum.info, infoSubject.getType());
        Message emptySubject = new Message(Message.MessageEnum.info, "");
        assertEquals("", emptySubject.getText());
        Message longSubject = new Message(Message.MessageEnum.info, LONG_STRING);
        assertEquals(LONG_STRING, longSubject.getText());
    }
}
