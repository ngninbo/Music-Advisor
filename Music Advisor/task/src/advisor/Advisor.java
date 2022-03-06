package advisor;

import advisor.controller.AdvisorController;
import java.util.Scanner;

public class Advisor {

    private final AdvisorController advisorController;

    public Advisor(AdvisorController advisorController) {
        this.advisorController = advisorController;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            exit = advisorController.processCommand(new Scanner(System.in).nextLine());
        }
    }
}
