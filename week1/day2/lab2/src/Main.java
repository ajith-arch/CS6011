import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Print a greeting
        System.out.println("Hello, world!");

        // Initialize an array and populate it with integers
        int[] integers = new int[10];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = i;
            System.out.println("Element at index " + i + ": " + integers[i]);
        }

        // Calculate and print the sum of the array
        int sum = Arrays.stream(integers).sum();
        System.out.println("Sum of array elements: " + sum);

        // Collect user input for name and age
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String name = sc.nextLine();

        System.out.print("Please enter your age: ");
        int age;
        if (sc.hasNextInt()) {
            age = sc.nextInt();
        } else {
            System.out.println("Invalid input. Age must be a number.");
            return;
        }

        // Determine eligibility to vote
        if (age >= 18) {
            System.out.println("Hello " + name + ", you can vote!");
        } else {
            System.out.println("Hello " + name + ", you are not eligible to vote yet.");
        }

        // Determine generational classification
        if (age >= 96 && age <= 122) {
            System.out.println("You are part of the Greatest Generation.");
        } else if (age >= 61 && age <= 95) {
            System.out.println("You are part of the Baby Boomers.");
        } else if (age >= 41 && age <= 60) {
            System.out.println("You are part of Generation X.");
        } else if (age >= 29 && age <= 40) {
            System.out.println("You are a Millennial.");
        } else if (age >= 13 && age <= 28) {
            System.out.println("You are part of iGen.");
        } else {
            System.out.println("You are too young to be classified in these generations.");
        }

        // Close the scanner
        sc.close();
    }
}
