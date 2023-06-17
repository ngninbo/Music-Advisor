class TestDrive {
    public static void main(String[] args) {
        Breakfast bagel = new Bread();
        bagel = new Butter(bagel);
        bagel = new Cheese(bagel);
        System.out.println(bagel.getSummary());

        Breakfast bun = new Bread();
        bun = new Butter(bun);
        bun = new Jam(bun);
        System.out.println(bun.getSummary());
    }
}

interface Breakfast {
    String getDescription();

    int getKcal();

    default String getSummary() {
        return getDescription() + ". kCal: " + getKcal();
    }
}

class Bread implements Breakfast {
    String description = "Bread";
    int kCal = 200;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getKcal() {
        return kCal;
    }
}

class Butter implements Breakfast {
    private final Breakfast breakfast;

    Butter(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Butter on top of " + breakfast.getDescription();
    }

    @Override
    public int getKcal() {
        return breakfast.getKcal() + 50;
    }
}

class Jam implements Breakfast {
    private final Breakfast breakfast;

    Jam(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Jam on top of " + breakfast.getDescription();
    }

    @Override
    public int getKcal() {
        return breakfast.getKcal() + 120;
    }
}

class Cheese implements Breakfast {
    private final Breakfast breakfast;

    Cheese(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Cheese on top of " + breakfast.getDescription();
    }

    @Override
    public int getKcal() {
        return breakfast.getKcal() + 40;
    }
}