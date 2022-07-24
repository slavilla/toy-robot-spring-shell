package com.iress.toy.robot;

import com.iress.toy.robot.command.MoveCommandExecutor;
import com.iress.toy.robot.command.PlaceCommandExecutor;
import com.iress.toy.robot.command.ReportCommandExecutor;
import com.iress.toy.robot.command.TurnCommandExecutor;
import com.iress.toy.robot.model.CommandResponse;
import com.iress.toy.robot.model.Robot;
import com.iress.toy.robot.model.TableTop;
import com.iress.toy.robot.model.Turn;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Pattern;

@ShellComponent
public class RobotCommand {
    private final PlaceCommandExecutor placeCommand;
    private final TurnCommandExecutor turnCommand;
    private final MoveCommandExecutor moveCommand;
    private final ReportCommandExecutor reportCommand;
    private Robot robot;
    private TableTop tableTop;

    public RobotCommand(PlaceCommandExecutor placeCommand,
                        TurnCommandExecutor turnCommand,
                        MoveCommandExecutor moveCommand,
                        ReportCommandExecutor reportCommand) {
        this.placeCommand = placeCommand;
        this.turnCommand = turnCommand;
        this.moveCommand = moveCommand;
        this.reportCommand = reportCommand;
    }

    @PostConstruct
    public void init() {
        robot = new Robot();
        tableTop = new TableTop(5, 5);
    }

    @ShellMethod(value = "Places the robot in the table", prefix = "", key = {"PLACE"})
    public String place(@ShellOption(help = "Format: X,Y,F where F is either NORTH, EAST, SOUTH or WEST")
                        @Pattern(regexp = "^\\d+,\\d+,(NORTH|EAST|SOUTH|WEST)$",
                                message = "PLACE parameter format should be X,Y,F where F is either NORTH, EAST, SOUTH or WEST.")
                                String position) {
        CommandResponse response = placeCommand.execute(robot, tableTop, position);
        return response.isSuccess() ? null : response.getMessage();
    }

    @ShellMethod(value = "Shows robot's current location", prefix = "", key = {"REPORT"})
    public String report() {
        CommandResponse response = reportCommand.execute(robot, tableTop);
        return response.getMessage();
    }

    @ShellMethod(value = "Moves the toy robot one unit forward in the direction it is currently facing", prefix = "", key = {"MOVE"})
    public String move() {
        CommandResponse response = moveCommand.execute(robot, tableTop);
        return response.isSuccess() ? null : response.getMessage();
    }

    @ShellMethod(value = "Rotates the toy robot 90 degrees to the right", prefix = "", key = {"RIGHT"})
    public String right() {
        CommandResponse response = turnCommand.execute(robot, tableTop, Turn.RIGHT);
        return response.isSuccess() ? null : response.getMessage();
    }

    @ShellMethod(value = "Rotates the toy robot 90 degrees to the left", prefix = "", key = {"LEFT"})
    public String left() {
        CommandResponse response = turnCommand.execute(robot, tableTop, Turn.LEFT);
        return response.isSuccess() ? null : response.getMessage();
    }

    @ShellMethod(value = "Removes the toy robot from the table", prefix = "", key = {"REMOVE"})
    public void remove() {
        robot.setPosition(null);
    }
}
