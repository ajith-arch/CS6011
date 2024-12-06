"use strict";

function locateMinimum(arr, startIndex, compare) {
    let minIndex = startIndex;
    for (let i = startIndex + 1; i < arr.length; i++) {
        if (compare(arr[i], arr[minIndex])) {
            minIndex = i;
        }
    }
    return minIndex;
}

function sortSelection(arr, compare = (a, b) => a < b) {
    for (let i = 0; i < arr.length; i++) {
        let minIndex = locateMinimum(arr, i, compare);
        let temp = arr[minIndex];
        arr[minIndex] = arr[i];
        arr[i] = temp;
    }
    return arr;
}

class Individual {
    constructor(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

function compareIndividuals(ind1, ind2) {
    if (ind1.lastName === ind2.lastName) {
        return ind1.firstName < ind2.firstName;
    } else {
        return ind1.lastName < ind2.lastName;
    }
}

module.exports = {
    sortSelection,
    Individual,
    compareIndividuals,
};
