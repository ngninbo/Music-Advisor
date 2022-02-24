package advisor;

import advisor.controller.AdvisorController;
import advisor.controller.AdvisorControllerImpl;

public class AdvisorBuilder {

    private final String[] args;
    private AdvisorController advisorController;

    private AdvisorBuilder(String[] args) {
        this.args = args;
    }

    public static AdvisorBuilder init(String[] args) {
        return new AdvisorBuilder(args);
    }

    public AdvisorBuilder withAdvisorController() {
        this.advisorController = new AdvisorControllerImpl(args);
        return this;
    }

    public Advisor build() {
        return new Advisor(advisorController);
    }
}
