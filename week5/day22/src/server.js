// server.js
const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });

wss.on('connection', (ws) => {
  console.log('A client connected');

  // Handle incoming messages
  ws.on('message', (message) => {
    const parsedMessage = JSON.parse(message);

    // Handle join message
    if (parsedMessage.type === 'join') {
      const joinMessage = {
        type: 'system',
        text: `${parsedMessage.username} has joined the room: ${parsedMessage.room}`,
      };

      // Broadcast join message to all clients
      wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(joinMessage));
        }
      });
    }
    // Handle regular chat messages
    else if (parsedMessage.type === 'message') {
      const messageToBroadcast = {
        type: 'message',
        text: parsedMessage.text,
        username: parsedMessage.username,
        room: parsedMessage.room,
      };

      // Broadcast chat message to all clients
      wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(messageToBroadcast));
        }
      });
    }
  });

  // Handle client disconnect
  ws.on('close', () => {
    console.log('A client disconnected');
  });
});

// Handle errors in WebSocket server
wss.on('error', (error) => {
  console.error(`WebSocket server error: ${error.message}`);
});

console.log('WebSocket server is running on ws://localhost:8080');

//ajith//