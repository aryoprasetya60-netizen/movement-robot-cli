package repository;

import entity.Robot;

import java.io.IOException;
import java.nio.file.Path;
import java.security.spec.InvalidKeySpecException;

public interface RobotRepository {
    void changeDirection(Path path, String commit, Robot robot) throws IOException;
    String setNowPosition(Path path, Robot robot) throws IOException;
}
