// Get DOM elements
let join = document.getElementById('join');
let leave = document.getElementById('leave');

//st

let userObj = document.getElementById('user-entry');
let roomObj = document.getElementById('room-entry');
let homePage = document.getElementById("home-page");
let chatPage = document.getElementById("chat-page");
let chatForm = document.getElementById("chat-form");
///


let formMessage = document.getElementById("chat-message");
let chatLog = document.getElementById("chat-log");

// Establish WebSocket connection
let ws = new WebSocket("ws://localhost:8080");
let isConnected = false;
let userName;

// Function to create chat log entries
function createLogEntry(message, user) {


    let messageContainer = document.createElement("div");
    messageContainer.className = "message-container";

    // Message content
    let messageBlock = document.createElement("p");
    messageBlock.className = "message-body";
    messageBlock.innerHTML = message;

    // Meta info
    let messageMeta = document.createElement("div");
    let messageUser = document.createElement("span");
    messageUser.innerHTML = `${user} `;



    let messageDtm = document.createElement("span");
    messageDtm.innerHTML = `@ ${new Date().toLocaleTimeString()}`;
    messageMeta.appendChild(messageUser);
    messageMeta.appendChild(messageDtm);

    // Combine content and meta info
    messageContainer.appendChild(messageBlock);
    messageContainer.appendChild(messageMeta);
    chatLog.appendChild(messageContainer);

    // Scroll to the latst message
    chatLog.scrollTop = chatLog.scrollHeight;
}

// Send chat messges
function submitChatMessage(event) {
    event.preventDefault();
    if (formMessage.value.trim()) {
        ws.send(`message ${formMessage.value}`);
        chatForm.reset();
    }
}

// WebSocket even
ws.onopen = function () {
    isConnected = true;
    console.log("WebSocket connection established");
};

ws.onmessage = function (messageEvent) {
    let message = JSON.parse(messageEvent.data);
    if (message.type === "join") {


        chatPage.style.display = "block";
        homePage.style.display = "none";
        document.getElementById("room").innerHTML = `Room: ${message.room}`;
        chatForm.addEventListener("submit", submitChatMessage);
    }
    if (message.type === "message") {
        createLogEntry(message.message, message.user);
    }
};

ws.onerror = function () {
    console.log("Error occurred in WebSocket connection.");
};

//
ws.onclose = function () {


    console.log("WebSocket connection closed.");
};

// Validate username and room innputs
function validateUserInput(value) {
    return /^[a-zA-Z]+$/.test(value);
}

// Handle join action
function getUsernameAndRoom() {
    userName = userObj.value.trim();
    let room = roomObj.value.trim();

    if (!validateUserInput(userName) || !validateUserInput(room)) {
        alert("Username and Room must only contain letters.");
    } else {
        ws.send(`join ${userName} ${room}`);
    }
}

//** */


// Handle leave actionn
function leaveChatRoom() {
    chatPage.style.display = "none";
    homePage.style.display = "block";
}

leave.addEventListener('click', leaveChatRoom);


////msg
join.addEventListener('click', getUsernameAndRoom);
