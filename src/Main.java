import entity.Robot;
import repository.RobotRepository;
import repository.impl.RobotRepositoryImpl;
import service.RobotService;
import service.impl.RobotServiceImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws IOException, InvalidKeySpecException {
    RobotRepository robotRepository = new RobotRepositoryImpl();
    RobotService robotService = new RobotServiceImpl(robotRepository);
    Path path = Path.of("src","resource", "move.txt");
    try {
        robotService.ChangePosition(path,"A");
    } catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (InvalidKeySpecException e) {
        System.out.println(e.getMessage());
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
}