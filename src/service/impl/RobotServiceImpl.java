package service.impl;

import entity.Robot;
import repository.RobotRepository;
import service.RobotService;

import java.nio.file.Path;

public class RobotServiceImpl implements RobotService {
    RobotRepository robotRepository;

    public RobotServiceImpl(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @Override
    public void ChangePosition(Path path, String commit, Robot robot) {

    }
}
