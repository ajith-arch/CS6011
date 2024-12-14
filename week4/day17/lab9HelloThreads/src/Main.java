public class Main {
    public static void main(String[] args) throws InterruptedException {
        sayHello();
    }

    public static void sayHello() throws InterruptedException {
        // Create an array of threads
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        // Initialize each thread to count to 100 and print messages
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    if (j % 10 == 0) {
                        System.out.print("hello number " + j + " from thread number " + Thread.currentThread().getId() + "\n");
                    } else {
                        System.out.print("hello number " + j + " from thread number " + Thread.currentThread().getId() + " ");
                    }
                }
            });
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Join all threads
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nAll threads completed!");
    }
}
