package service.impl;

import entity.Robot;
import repository.RobotRepository;
import service.RobotService;
import utils.exception.NullPointerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;

public class RobotServiceImpl implements RobotService {
    RobotRepository robotRepository;

    public RobotServiceImpl(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @Override
    public void changePosition(Path path, String commit) throws IOException, InvalidKeySpecException {
        //validasi commit
        if (commit.trim().isEmpty()){
            throw new NullPointerException("Commit Tidak Boleh Kosong!");
        }
        String[] initiateDirection = {"NORTH","EAST","SOUTH","WEST"};
        String getDirection;
        int getX;
        int getY;
        Robot dataRobot1 = robotRepository.getNowPosition(path);

        getDirection = dataRobot1.getDirection();
        getX = dataRobot1.getX();
        getY = dataRobot1.getY();

        switch (commit){
            case "A":
                if (getDirection.equals("NORTH")){
                    getY += 1;
                } else if (getDirection.equals("SOUTH")) {
                    getY -= 1;
                } else if (getDirection.equals("EAST")) {
                    getX += 1;
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
            case "0":
                System.out.println("=========== COMMAND DIBATALKAN ===========");
                return;
            default:
                throw new InvalidKeySpecException("Input Salah!(R, L, A)");
        }
        Robot newRobot = new Robot(getDirection,getX,getY);
        robotRepository.changeDirection(path, commit, newRobot);
        System.out.printf("%s -> {%s, %d, %d}\n",commit, getDirection, getX, getY);
        System.out.println("=========== COMMIT BERHASIL! ===========");
    }

    @Override
    public void checkPosition(Path path) throws IOException {

        Robot dataRobot1 = robotRepository.getNowPosition(path);
        if (Files.exists(path)) {
            String getDirection = dataRobot1.getDirection();
            System.out.println("Arah robot: " + getDirection);

            int getX = dataRobot1.getX();
            System.out.println("Arah X: " + getX);

            int getY = dataRobot1.getY();
            System.out.println("Arah Y: " + getY);
            System.out.printf("%s { %d , %d }\n", getDirection, getX, getY);
        } else {
            throw new IOException("File Doesn't Exist");
        }
    }

    @Override
    public void checkAllHistory(Path path) throws IOException {
        List<Robot> robots = robotRepository.getAllHistory(path);
        List<String> commands = robotRepository.getAllCommand(path);
        if (path!=null){
            int no=1;
            System.out.println("Total Commands: "+commands.size());
            System.out.println("==================================================================================");
            System.out.println("||NO\t||\tCommand\t\t||\tArah Robot \t||\t Koordinat X \t||\t Koordinat Y \t||");
            System.out.println("==================================================================================");
            for (int i=0;i<commands.size();i++){
                if (i==0){
                    System.out.println("|| "+no+"  \t||\t"+commands.get(i)+"\t||\t"+robots.get(i).getDirection()+"\t\t||\t\t"+robots.get(i).getX()+"\t\t\t||\t\t"+robots.get(i).getY()+"\t\t\t||");
                } else {
                    System.out.println("|| "+no+"  \t||\t"+commands.get(i)+"\t\t\t||\t"+robots.get(i).getDirection()+"\t\t||\t\t"+robots.get(i).getX()+"\t\t\t||\t\t"+robots.get(i).getY()+"\t\t\t||");
                }
                no+=1;
            }
            System.out.println("==================================================================================");
        }
    }

    @Override
    public void resetRobot(Path path) throws IOException {
        if (path!=null){
            boolean cekRobot = robotRepository.resetRobot(path);
            if (!cekRobot){
                throw new IOException("File doesn't exist");
            }
            System.out.println("========= ROBOT BERHASIL DIRESET =========");
        } else {
            throw new NullPointerException("Path Tidak Boleh Kosong");
        }
    }

    @Override
    public void createNewDirectory(Path path, Robot robot) throws IOException, InvalidKeySpecException {
        if (path==null){
            throw new IOException("Path Tidak Boleh Kosong");
        }
        switch (robot.getDirection()){
            case "SOUTH", "WEST", "EAST","NORTH":
                robotRepository.createDirectory(path,robot);
                break;
            default:
                throw new InvalidKeySpecException("Input Salah!(SOUTH, WEST, EAST,NORTH");
        }
    }
}
