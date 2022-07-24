package com.iress.toy.robot.command;

import com.iress.toy.robot.model.*;
import org.springframework.stereotype.Component;

@Component
public class PlaceCommandExecutor {

    public CommandResponse execute(Robot robot, TableTop tableTop, String params) {
        CommandResponse response = new CommandResponse(true, "");
        String[] subParams = params.split(",");

        int x = Integer.parseInt(subParams[0]);
        int y = Integer.parseInt(subParams[1]);
        Heading heading = Heading.get(subParams[2]);

        if (tableTop.isOutside(x, y)) {
            response.setSuccess(false);
            String message = String.format("Can't place robot outside the table of size %sx%s",
                    tableTop.getWidth(), tableTop.getHeight());
            response.setMessage(message);

        } else {
            robot.setPosition(new Position(x, y, heading));
        }
        return response;
    }
}
