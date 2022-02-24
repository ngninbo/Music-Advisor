import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SelectionContext {

    private PersonSelectionAlgorithm algorithm;

    public void setAlgorithm(PersonSelectionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Person[] selectPersons(Person[] persons) {
        return algorithm.select(persons);
    }
}

interface PersonSelectionAlgorithm {

    Person[] select(Person[] persons);
}

class TakePersonsWithStepAlgorithm implements PersonSelectionAlgorithm {

    private final int step;
    
    public TakePersonsWithStepAlgorithm(int step) {
        this.step = step;
    }

    @Override
    public Person[] select(Person[] persons) {

        List<Person> personList = new ArrayList<>();

        if (step == 1) {
            return persons;
        } else {
            int i = 0;
            do {
                personList.add(persons[i]);
                i += step;
            } while (i < persons.length);
        }

        Person[] result = new Person[personList.size()];

        return personList.toArray(result);
    }
}


class TakeLastPersonsAlgorithm implements PersonSelectionAlgorithm {

    private final int count;
    
    public TakeLastPersonsAlgorithm(int count) {
        this.count = count;
    }

    @Override
    public Person[] select(Person[] persons) {

        Person[] person = new Person[count];
        int j = 0;
        for (int i = persons.length - count; i < persons.length; i++) {
            if (j < person.length) {
                person[j] = persons[i];
            }
            j++;
        }

        return person;
    }
}

class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final int count = Integer.parseInt(scanner.nextLine());
        final Person[] persons = new Person[count];

        for (int i = 0; i < count; i++) {
            persons[i] = new Person(scanner.nextLine());
        }

        final String[] configs = scanner.nextLine().split("\\s+");

        final PersonSelectionAlgorithm alg = create(configs[0], Integer.parseInt(configs[1]));
        SelectionContext ctx = new SelectionContext();
        ctx.setAlgorithm(alg);

        final Person[] selected = ctx.selectPersons(persons);
        for (Person p : selected) {
            System.out.println(p.name);
        }
    }

    public static PersonSelectionAlgorithm create(String algType, int param) {
        switch (algType) {
            case "STEP": {
                return new TakePersonsWithStepAlgorithm(param);
            }
            case "LAST": {
                return new TakeLastPersonsAlgorithm(param);
            }
            default: {
                throw new IllegalArgumentException("Unknown algorithm type " + algType);
            }
        }
    }
}
