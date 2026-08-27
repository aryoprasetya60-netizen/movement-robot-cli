package service;

import entity.Robot;

import java.io.IOException;
import java.nio.file.Path;
import java.security.spec.InvalidKeySpecException;

public interface RobotService {
    void ChangePosition(Path path, String commit) throws IOException, InvalidKeySpecException;
}
