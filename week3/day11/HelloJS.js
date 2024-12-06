// Display "Hello World" using document.writeln
document.writeln("Hello World");

// Display "Hello World" using console.log


// Difference between document.writeln and console.log:
// - `document.writeln()` writes directly into the HTML document. It changes the visual content of the webpage.
// - `console.log()` writes to the browser's developer console. It is useful for debugging and does not affect the displayed webpage.

// Create an array literal
let myArray = ["String", true, 42, 3.14, { key: "value" }];

// Print the array
console.log("Original Array:", myArray);

// Modify the array (e.g., add a new element)
myArray.push("New Element");

// Print the modified array
console.log("Modified Array:", myArray);

// Examine the console:
// You may notice the browser dynamically references the updated state of objects when "opening" the printed output in the console.

// Define a function (C++ style)
function add(a, b) {
    return a + b;
}

// Define a function (function literal syntax)
let myFunction = function(a, b) {
    return a + b;
};

// Test the functions
console.log("add(3, 4):", add(3, 4));                // Integers
console.log("myFunction(3.1, 4.2):", myFunction(3.1, 4.2)); // Floating-point numbers
console.log("add('Hello ', 'World'):", add("Hello ", "World")); // Strings
console.log("myFunction(3, ' apples'):", myFunction(3, " apples")); // Mix

// Observation:
// Both syntaxes work similarly in most cases. The C++ style syntax is hoisted, meaning it can be called before its definition. 
// The function literal syntax is not hoisted, so it must be defined before use.
