package com.iress.toy.robot.command;

import com.iress.toy.robot.model.*;
import org.springframework.stereotype.Component;

@Component
public class TurnCommandExecutor {

    public CommandResponse execute(Robot robot, TableTop tableTop, Turn turn) {
        CommandResponse response = new CommandResponse(true, "");
        Position position = robot.getPosition();

        if (position == null) {
            return new CommandResponse(false, "Place the robot first in the table");
        }

        Heading heading = position.getHeading();

        int newHeadingOrdinal2 = heading.ordinal() + (turn == Turn.RIGHT ? 1 : -1);

        if (newHeadingOrdinal2 == Heading.values().length) {
            newHeadingOrdinal2 = 0;

        } else if (newHeadingOrdinal2 < 0) {
            newHeadingOrdinal2 = Heading.values().length - 1;
        }
        position.setHeading(Heading.values()[newHeadingOrdinal2]);
        return response;
    }
}
