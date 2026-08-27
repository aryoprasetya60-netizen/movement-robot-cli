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
        String[] initiateDirection = {"NORTH","EAST","SOUTH","WEST"};
        String getDirection = "NORTH";
        int getX = 0;
        int getY = 0;
        Robot dataRobot1 = robotRepository.getNowPosition(path);

            getDirection = dataRobot1.getDirection();
            System.out.println("Arah robot: "+getDirection);

            getX = dataRobot1.getX();
            System.out.println("Arah X: "+getX);

            getY = dataRobot1.getY();
            System.out.println("Arah Y: "+getY);


        switch (commit){
            case "A":
                if (getDirection.equals("NORTH")){
                    getY += 1;
                } else if (getDirection.equals("SOUTH")) {
                    getY -= 1;
                } else if (getDirection.equals("EAST")) {
                    getX += 1;
                    System.out.println(getX);
                } else if (getDirection.equals("WEST")) {
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
                        } else {
                            getDirection = initiateDirection[i-1];
                        }
                        break;
                    }
                }
                break;
            default:
                throw new InvalidKeySpecException("Input Salah!(R, L, A)");
        }
        System.out.println("Get X:"+getX);
        Robot newRobot = new Robot(getDirection,getX,getY);
        robotRepository.changeDirection(path, commit, newRobot);
    }
}
