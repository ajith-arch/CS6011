public class Danger {
    // Shared variable
    private static int answer = 0;

    public static void main(String[] args) throws InterruptedException {
        badSum();
    }

    public static void badSum() throws InterruptedException {
        // Initialize variables
        int maxValue = 40000;
        int numThreads = 10;
        answer = 0;

        // Correct answer using Euler's formula
        int correctAnswer = maxValue * (maxValue - 1) / 2;
        System.out.println("Correct Answer: " + correctAnswer);

        // Create threads
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i; // Use final variable for lambda
            threads[i] = new Thread(() -> {
                int start = threadIndex * maxValue / numThreads;
                int end = Math.min((threadIndex + 1) * maxValue / numThreads, maxValue);

                for (int j = start; j < end; j++) {
                    answer += j; // Not thread-safe!
                }
            });
        }

        // Start threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Join threads
        for (Thread thread : threads) {
            thread.join();
        }

        // Print results
        System.out.println("Computed Answer: " + answer);
        System.out.println("Does it match? " + (answer == correctAnswer));
    }
}
