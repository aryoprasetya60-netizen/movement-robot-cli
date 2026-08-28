import entity.Robot;
import repository.RobotRepository;
import repository.impl.RobotRepositoryImpl;
import service.RobotService;
import service.impl.RobotServiceImpl;
import utils.exception.NullPointerException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws IOException, InvalidKeySpecException {
    Scanner scanner = new Scanner(System.in);
    RobotRepository robotRepository = new RobotRepositoryImpl();
    RobotService robotService = new RobotServiceImpl(robotRepository);
    Path path = Path.of("src","resource", "move.txt");
    boolean repeat = true;
    while (repeat) {
        System.out.println();
        System.out.println("===========================================");
        System.out.println("              ROBOT MOVEMENT               ");
        System.out.println("===========================================");
        System.out.println("1. COMMIT MOVEMENT                         ");
        System.out.println("2. CHECK CURRENT POSITION                  ");
        System.out.println("3. CHECK SEMUA COMMAND                     ");
        System.out.println("4. RESET ROBOT                             ");
        System.out.println("0. Keluar                                  ");
        System.out.println("===========================================");

        System.out.print("Pilih Menu    : ");
        String menu = scanner.nextLine();

        switch (menu) {
            case "1":
                commitMovement(robotRepository, robotService, scanner, path);
                break;
            case "2":
                checkCurrentPosition(robotService, path);

                break;
            case "3":
                checkAllCommand(robotService, path);

                break;
            case "4":
                resetRobot(robotService, path);

                break;
            case "0":
                System.out.println();
                System.out.println("===========================================");
                System.out.println("Terima kasih telah menggunakan sistem kami!");
                System.out.println("===========================================");
                System.out.println();

                repeat = false;

                break;
            default:
                System.out.println("Menu tidak tersedia!");
                System.out.println("Silakan pilih menu 0-3.");
        }
    }

    scanner.close();

}
public static void commitMovement(RobotRepository robotRepository, RobotService robotService, Scanner scanner, Path path) throws IOException, InvalidKeySpecException {
    System.out.println("  =======================================");
    System.out.println("              COMMMAND ROBOT             ");
    System.out.println("  =======================================");
    if (robotRepository.getNowPosition(path)==null){
        System.out.println("ROBOT MASIH KOSONG!!");
        System.out.println("MASUKKAN INISIASI POSISI ROBOT!");
        System.out.print("Input Direction(NORTH,SOUTH,EAST,WEST):");
        String direction = scanner.nextLine();
        System.out.print("Input Position X:");
        int initiateX = scanner.nextInt();
        System.out.print("Input Position Y:");
        int initiateY = scanner.nextInt();
        scanner.nextLine();
        try {
            Robot newRobot = new Robot(direction.toUpperCase(), initiateX, initiateY);
            robotService.createNewDirectory(path, newRobot);
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InvalidKeySpecException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    } else {
        System.out.print("A = Maju 1 Langkah \n");
        System.out.print("L = Hadap Kiri \n");
        System.out.print("R = Hadap Kanan \n");
        System.out.print("0 = Batal \n");
        System.out.print("Masukkan Commands: ");
        String command = scanner.nextLine();
        if (command != "0") {
            try {
                robotService.changePosition(path, command.toUpperCase());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InvalidKeySpecException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

public static void checkCurrentPosition(RobotService robotService, Path path){
    System.out.println("  =======================================");
    System.out.println("              CHECK POSITION             ");
    System.out.println("  =======================================");
    try {
        robotService.checkPosition(path);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
}

public static void checkAllCommand(RobotService robotService, Path path){
    System.out.println("  =======================================");
    System.out.println("              COMMAND HISTORY            ");
    System.out.println("  =======================================");
    try {
        robotService.checkAllHistory(path);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
}
public static void resetRobot(RobotService robotService, Path path){
    System.out.println("  =======================================");
    System.out.println("                RESET ROBOT              ");
    System.out.println("  =======================================");
    try {
        robotService.resetRobot(path);
    } catch (NullPointerException e) {
        System.out.println(e.getMessage());
    }catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
}