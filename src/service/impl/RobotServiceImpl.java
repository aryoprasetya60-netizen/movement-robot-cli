package service.impl;

import entity.Robot;
import repository.RobotRepository;
import service.RobotService;

import java.io.IOException;
import java.nio.file.Path;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

public class RobotServiceImpl implements RobotService {
    RobotRepository robotRepository;

    public RobotServiceImpl(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @Override
    public void ChangePosition(Path path, String commit) throws IOException, InvalidKeySpecException {
        //validasi commit
        String dataRobot1 = robotRepository.getNowPosition(path);
        String[] parts = dataRobot1.split(",");
        String[] initiateDirection = {"NORTH","EAST","SOUTH","WEST"};
        String getDirection = parts[0];
        System.out.println("Arah robot: "+getDirection);
        int getX = Integer.parseInt(parts[1]);
        System.out.println("Arah X: "+getX);
        int getY = Integer.parseInt(parts[2]);
        System.out.println("Arah Y: "+getY);
        switch (commit){
            case "A":
                if (getDirection == "NORTH"){
                    getY += 1;
                } else if (getDirection == "SOUTH") {
                    getY -= 1;
                } else if (getDirection == "EAST") {
                    getX += 1;
                } else if (getDirection == "WEST") {
                    getX -= 1;
                }
                break;
            case "R":
                for (int i = 0; i < initiateDirection.length; i++) {
                    if (Objects.equals(getDirection, initiateDirection[i])){
                        if (i == initiateDirection.length-1){
                            getDirection = initiateDirection[0];
                        } else {
                            getDirection = initiateDirection[i + 1];
                        }
                        break;
                    }
                }
                break;
            case "L":
                for (int i = 0; i < initiateDirection.length; i++) {
                    if (Objects.equals(getDirection, initiateDirection[i])){
                        if (i == 0){
                            getDirection = initiateDirection[initiateDirection.length-1];
                        }
                        getDirection = initiateDirection[i-1];
                        break;
                    }
                }
                break;
            default:
                throw new InvalidKeySpecException("Input Salah!(R, L, A)");
        }
        System.out.println("Get X"+getX);
        Robot newRobot = new Robot(getDirection,getX,getY);
        robotRepository.changeDirection(path, commit, newRobot);
    }
}
