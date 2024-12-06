console.log("Hello World");
console.log("hello MSD");


// Step 1: Define an array of numbers
let numbers = [10, 25, 45, 2, 89, 67, 14];

// Step 2: Find the maximum number using a loop
let maxNumber = numbers[0]; // Assume the first number is the largest
for (let i = 1; i < numbers.length; i++) {
    if (numbers[i] > maxNumber) {
        maxNumber = numbers[i];
    }
}

// Step 3: Print the largest number
console.log("Maximum number using loop:", maxNumber);

// Step 4: Find the maximum number using Math.max and the spread operator
let maxUsingMath = Math.max(...numbers);
console.log("Maximum number using Math.max:", maxUsingMath);
