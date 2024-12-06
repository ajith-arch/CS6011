const { sortSelection, Individual, compareIndividuals } = require("./app.js");

function compareArrays(arr1, arr2) {
    if (arr1.length !== arr2.length) return false;
    for (let i = 0; i < arr1.length; i++) {
        if (arr1[i] !== arr2[i]) return false;
    }
    return true;
}

// Integer Sorting
let numbers = [6, 3, 8, 1, 5];
let sortedNumbers = sortSelection(numbers);
let expectedNumbers = [1, 3, 5, 6, 8];
console.assert(compareArrays(sortedNumbers, expectedNumbers), "Integer sorting failed");

// Floating-point Sorting
let decimals = [7.7, 1.1, 4.4, 9.9, 3.3];
let sortedDecimals = sortSelection(decimals);
let expectedDecimals = [1.1, 3.3, 4.4, 7.7, 9.9];
console.assert(compareArrays(sortedDecimals, expectedDecimals), "Float sorting failed");

// String Sorting
let words = ["zebra", "apple", "orange", "banana", "grape"];
let sortedWords = sortSelection(words);
let expectedWords = ["apple", "banana", "grape", "orange", "zebra"];
console.assert(compareArrays(sortedWords, expectedWords), "String sorting failed");

// Descending Order Sorting
let descending = (a, b) => a > b;
let sortedDescending = sortSelection(numbers, descending);
let expectedDescending = [8, 6, 5, 3, 1];
console.assert(compareArrays(sortedDescending, expectedDescending), "Descending sorting failed");

// Individual Sorting
let person1 = new Individual("Emma", "Brown");
let person2 = new Individual("Liam", "Davis");
let person3 = new Individual("Olivia", "Adams");
let person4 = new Individual("Noah", "Smith");
let person5 = new Individual("Sophia", "Brown");

let individuals = [person1, person2, person3, person4, person5];
let sortedIndividuals = sortSelection(individuals, compareIndividuals);
let expectedIndividuals = [person3, person5, person1, person2, person4];
console.assert(compareArrays(sortedIndividuals, expectedIndividuals), "Individual sorting failed");
