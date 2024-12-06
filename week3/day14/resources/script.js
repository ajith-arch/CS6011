let x = document.getElementById("xVal");
let y = document.getElementById("yVal");
let submit = document.getElementById("calculate");
let result = document.getElementById("result");

let ws = new WebSocket("ws://localhost:8080");
let isConnected = false;

// WebSocket event handlers
ws.onopen = function () {
    isConnected = true;
    console.log("WebSocket connection established.");
};

ws.onmessage = function (messageEvent) {
    console.log("Message received from server:", messageEvent.data);
    result.textContent = "Result (WebSocket): " + messageEvent.data;
};

ws.onerror = function () {
    console.error("WebSocket error occurred.");
    alert("WebSocket error. Please try again.");
};

ws.onclose = function () {
    isConnected = false;
    console.log("WebSocket connection closed.");
};

// Handle button click
submit.addEventListener("click", function () {
    let xValue = Number(x.value);
    let yValue = Number(y.value);

    if (!isNaN(xValue) && !isNaN(yValue)) {
        // Query string approach
        fetch(`http://localhost:8080/calculate?x=${xValue}&y=${yValue}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Query String request failed.");
                }
                return response.text();
            })
            .then(data => {
                console.log("Query String result:", data);
                result.textContent = "Result (Query String): " + data;
            })
            .catch(error => {
                console.error(error);
                alert("Failed to fetch result via Query String.");
            });

        // WebSocket approach
        if (isConnected) {
            ws.send(`${xValue} ${yValue}`);
        } else {
            alert("WebSocket is not connected.");
        }
    } else {
        alert("Please enter valid numbers for X and Y.");
    }
});

