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
            createDirectory(path,robot);
        }
        String changeCoordinate = String.format("%s -> {%s, %d, %d}",
                commit,
                robot.getDirection(),
                robot.getX(),
                robot.getY());
        Files.writeString(path, changeCoordinate + System.lineSeparator(), StandardOpenOption.APPEND);
    }

    @Override
    public List<Robot> getAllHistory(Path path) throws IOException {
        List<Robot> robots = new ArrayList<>();
        if (!Files.exists(path)){
            throw new IOException("Files doesn't exist");
        }
        if (path != null && Files.exists(path)) {
            List<String> historyList = Files.readAllLines(path);

            for (int i = 1; i < historyList.size(); i++) {
                String getCoordinate = historyList.get(i);
                String data = getCoordinate.substring(
                        getCoordinate.indexOf("{")+1,
                        getCoordinate.indexOf("}")
                );
                String[] parts = data.split(",");
                if (parts[0].trim()!="" && parts[1].trim()!="") {
                    robots.add(
                            new Robot(
                                parts[0].trim(),
                                Integer.parseInt(parts[1].trim()),
                                Integer.parseInt(parts[2].trim())
                            )
                    );
                }

            }
        }else{
            throw new IOException();
        }
        return robots;
    }

    public List<String> getAllCommand(Path path) throws IOException {
        List<String> commands = new ArrayList<>();

        if (path != null && Files.exists(path)) {
            List<String> commandList = Files.readAllLines(path);

            for (int i = 1; i < commandList.size(); i++) {
                String getCoordinate = commandList.get(i);
                String[] parts = getCoordinate.split("-");
                if (parts[0].trim()!="" && parts[1].trim()!="") {
                    commands.add(parts[0].trim());
                }

            }
        }else{
            throw new IOException();
        }
        return commands;
    }

    @Override
    public Robot getNowPosition(Path path) throws IOException {
        String direction = "NORTH";
        int getX = 0;
        int getY = 0;
        if (!Files.exists(path)) {
            return null;
        }

        List<String> commitList = Files.readAllLines(path);
        int lastLine = commitList.size() - 1;
        if (path != null) {
            String initialCoordinate = commitList.get(lastLine);
            String data = initialCoordinate.substring(
                    initialCoordinate.indexOf("{")+1,
                    initialCoordinate.indexOf("}")
            );
            String[] parts = data.trim().split(",");
            if (parts[0].trim() != "" && parts[1].trim() != "") {
                direction = parts[0].trim();
                getX = Integer.parseInt(parts[1].trim());
                getY = Integer.parseInt(parts[2].trim());
            }
        } else {
            throw new IOException();
        }
        Robot nowPosition = new Robot(direction,getX,getY);
        return nowPosition;
    }

    @Override
    public boolean resetRobot(Path path) throws IOException {
        if (Files.exists(path)){
            Files.delete(path);
            return true;
        } else {
            return false;
        }
    }

    public void createDirectory(Path path, Robot robot) throws IOException{
        String direction = "NORTH";
        int getX = 0;
        int getY = 0;
        if (robot!=null){
            direction = robot.getDirection();
            getX = robot.getX();
            getY = robot.getY();
        }
        Files.createFile(path);
        String headerCoordinate =
                String.format("Commit -> {Arah, X, Y}");
        Files.writeString(path, headerCoordinate + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        String initialCoordinate =
                String.format("INITIATE -> {%s, %d, %d}",direction,getX,getY);
        Files.writeString(path, initialCoordinate + System.lineSeparator() , StandardOpenOption.APPEND);
    }
}
