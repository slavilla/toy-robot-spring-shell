package com.iress.toy.robot.command;

import com.iress.toy.robot.model.CommandResponse;
import com.iress.toy.robot.model.Position;
import com.iress.toy.robot.model.Robot;
import com.iress.toy.robot.model.TableTop;
import org.springframework.stereotype.Component;

@Component
public class ReportCommandExecutor {

    public CommandResponse execute(Robot robot, TableTop tableTop) {
        CommandResponse response = new CommandResponse(true, "");
        Position position = robot.getPosition();

        if (position == null) {
            return new CommandResponse(false, "Place the robot first in the table");
        }
        response.setMessage("Output: " + position);
        return response;
    }
}
