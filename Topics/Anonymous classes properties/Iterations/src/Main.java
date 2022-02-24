
class IteratorExecutor {

    static void performIterationsWithCallback(int numberOfIterations, LoopCallback callback) {
        for (int i = 0; i < numberOfIterations; i++) {
            callback.onNewIteration(i);
        }
    }

    static void startIterations(int numberOfIterations) {

        performIterationsWithCallback(numberOfIterations, iteration -> System.out.println("Iteration: " + iteration));
    }
}

// Don't change the code below
interface LoopCallback {

    void onNewIteration(int iteration);
}