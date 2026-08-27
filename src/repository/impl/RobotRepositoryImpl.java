package repository.impl;

import entity.Robot;
import repository.RobotRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class RobotRepositoryImpl implements RobotRepository {
    String nowCoordinate;
    @Override
    public void changeDirection(Path path, String commit, Robot robot) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
            String initialCoordinate =
                    String.format("{%s, %d, %d}",
                            robot.getDirection(),
                            robot.getX(),
                            robot.getY());
            Files.writeString(path, initialCoordinate + System.lineSeparator(), StandardOpenOption.CREATE);
        } else {
            String changeCoordinate = String.format("%s -> {%s, %d, %d}",
                    commit,
                    robot.getDirection(),
                    robot.getX(),
                    robot.getY());
            Files.writeString(path, changeCoordinate + System.lineSeparator(), StandardOpenOption.APPEND);
        }
        nowCoordinate = robot.getDirection()+","+robot.getX()+","+robot.getY();
        getNowPosition(path);
    }

    @Override
    public String getNowPosition(Path path) {
        return nowCoordinate;
    }
}
