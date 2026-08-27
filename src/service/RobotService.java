package service;

import entity.Robot;

import java.nio.file.Path;

public interface RobotService {
    void ChangePosition(Path path, String commit, Robot robot);
}
