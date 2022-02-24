package advisor;

import advisor.controller.AdvisorController;

public class Advisor {

    private final AdvisorController advisorController;

    public Advisor(AdvisorController advisorController) {
        this.advisorController = advisorController;
    }

    public void start() {
        advisorController.start();
    }
}