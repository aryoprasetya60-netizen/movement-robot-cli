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
        }
        String changeCoordinate = String.format("%s -> {%s, %d, %d}",
                commit,
                robot.getDirection(),
                robot.getX(),
                robot.getY());
        Files.writeString(path, changeCoordinate + System.lineSeparator(), StandardOpenOption.APPEND);

        setNowPosition(path, robot);
    }

    @Override
    public String setNowPosition(Path path, Robot robot) throws IOException {
        String nowPosition;
        List<String> commitList = Files.readAllLines(path);
        int lastLine = commitList.size()-1;
        String[] parts = commitList.get(lastLine).split(",");
        if (parts[0].trim()!="" && parts[1].trim()!=""){
            nowPosition = parts[0].trim()+","+parts[1].trim()+","+parts[2].trim();
        } else {
            return null;
        }
        return nowPosition;
    }
}
