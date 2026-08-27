package repository;

import entity.Robot;

import java.io.IOException;
import java.nio.file.Path;


public interface RobotRepository {
    void changeDirection(Path path, String commit, Robot robot) throws IOException;
    Robot getNowPosition(Path path) throws IOException;
}
