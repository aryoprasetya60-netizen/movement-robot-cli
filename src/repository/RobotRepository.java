package repository;

import entity.Robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface RobotRepository {
    void changeDirection(Path path, String commit, Robot robot) throws IOException;
    List<Robot> getAllHistory(Path path) throws IOException;
    List<String> getAllCommand(Path path) throws IOException;
    Robot getNowPosition(Path path) throws IOException;
    void resetRobot(Path path) throws IOException;
}
