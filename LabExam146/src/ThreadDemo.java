//2022f-bse-146
class NumberPrinter {
    private int number = 1;
    private final int LIMIT = 20; // Immutable constant

    // Synchronized method ensures thread-safe printing
    public synchronized void printOdd() {
        while (number <= LIMIT) {
            if (number % 2 == 0) {
                try {
                    wait(); 
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " - Odd: " + number);
                number++;
                notify(); // Notify the other thread to proceed
            }
        }
    }

    public synchronized void printEven() {
        while (number <= LIMIT) {
            if (number % 2 != 0) {
                try {
                    wait(); // Wait if it's not the thread's turn
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " - Even: " + number);
                number++;
                notify(); // Notify the other thread to proceed
            }
        }
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter(); // Mutable object shared between threads

        // Thread for printing odd numbers
        Thread oddThread = new Thread(() -> printer.printOdd(), "OddThread");

        // Thread for printing even numbers
        Thread evenThread = new Thread(() -> printer.printEven(), "EvenThread");

        // Start the threads
        oddThread.start();
        evenThread.start();

        // Exception handling for thread termination
        try {
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("Number printing complete.");
    }
}