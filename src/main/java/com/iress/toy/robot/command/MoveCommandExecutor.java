package com.iress.toy.robot.command;

import com.iress.toy.robot.model.CommandResponse;
import com.iress.toy.robot.model.Position;
import com.iress.toy.robot.model.Robot;
import com.iress.toy.robot.model.TableTop;
import org.springframework.stereotype.Component;

@Component
public class MoveCommandExecutor {

    public CommandResponse execute(Robot robot, TableTop tableTop) {
        CommandResponse response = new CommandResponse(true, "");
        Position position = robot.getPosition();

        if (position == null) {
            return new CommandResponse(false, "Place the robot first in the table");
        }

        Position newPosition = new Position(position);

        switch (newPosition.getHeading()) {
            case EAST:
                newPosition.setX(newPosition.getX() + 1);
                break;
            case WEST:
                newPosition.setX(newPosition.getX() - 1);
                break;
            case NORTH:
                newPosition.setY(newPosition.getY() + 1);
                break;
            case SOUTH:
                newPosition.setY(newPosition.getY() - 1);
                break;
        }

        if (tableTop.isOutside(newPosition.getX(), newPosition.getY())) {
            response.setSuccess(false);
            String message = String.format("Can't move robot outside the table at coordinates %s, %s",
                    newPosition.getX(), newPosition.getY());
            response.setMessage(message);

        } else {
            robot.setPosition(newPosition);
        }
        return response;
    }
}
