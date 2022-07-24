package com.iress.toy.robot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.ParameterValidationException;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.shell.result.DefaultResultHandler;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class RobotCommandTest {

    @Autowired
    private Shell shell;

    @Autowired
    private DefaultResultHandler resultHandler;

    @Test
    void testValidPlaceCommand() {
        Object response = execute("PLACE 1,1,NORTH");
        assertNull(response);
    }

    @Test
    void testInvalidPlaceCommandParameter() {
        Object response = execute("PLACE 1,1,QQQ");
        assertTrue(response instanceof ParameterValidationException);
    }

    @Test
    void testInvalidPlaceCommandPosition() {
        Object response = execute("PLACE 11,22,NORTH");
        assertEquals("Can't place robot outside the table of size 5x5", response);
    }

    @Test
    void testCommandsWithNoPlacedRobot() {
        execute("REMOVE"); // Remove the robot from the table

        Object response = execute("REPORT");
        assertEquals("Place the robot first in the table", response);

        response = execute("MOVE");
        assertEquals("Place the robot first in the table", response);

        response = execute("RIGHT");
        assertEquals("Place the robot first in the table", response);

        response = execute("LEFT");
        assertEquals("Place the robot first in the table", response);
    }

    @Test
    void report() {
        Object response = execute("PLACE 2,2,EAST");
        assertNull(response);
        response = execute("REPORT");
        assertEquals("Output: 2,2,EAST", response);
    }

    private Object execute(String command) {
        Object response = shell.evaluate(() -> command);
        resultHandler.handleResult(response);
        return response;
    }

    @Test
    void move() {
        execute("PLACE 2,2,EAST");
        execute("MOVE");
        execute("PLACE 2,2,SOUTH");
        execute("MOVE");
        execute("PLACE 2,2,WEST");
        execute("MOVE");
        execute("PLACE 2,2,NORTH");
        execute("MOVE");
        Object response = execute("REPORT");
        assertEquals("Output: 2,3,NORTH", response);
    }

    @Test
    void moveOutsideTable() {
        execute("PLACE 2,2,NORTH");
        execute("MOVE");
        execute("MOVE");
        Object response = execute("MOVE");
        assertEquals("Can't move robot outside the table at coordinates 2, 5", response);
    }

    @Test
    void right() {
        execute("PLACE 2,2,EAST");
        execute("RIGHT");
        execute("RIGHT");
        execute("RIGHT");
        Object response = execute("REPORT");
        assertEquals("Output: 2,2,NORTH", response);
    }

    @Test
    void left() {
        execute("PLACE 2,2,EAST");
        execute("LEFT");//north
        execute("LEFT");//west
        execute("LEFT");//south
        execute("LEFT");//east
        execute("LEFT");//north
        Object response = execute("REPORT");
        assertEquals("Output: 2,2,NORTH", response);
    }
}