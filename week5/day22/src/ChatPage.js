// src/ChatPage.js
//ajith
//namee
import React, { useState, useEffect, useRef } from 'react';

function ChatPage({ username, room }) {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const socketRef = useRef(null);
  const messagesEndRef = useRef(null); // For auto-scrolling to the latest message
  const [isTyping, setIsTyping] = useState(false); // Typing indicator

  useEffect(() => {
    // Connecting to WebSocket server
    socketRef.current = new WebSocket('ws://localhost:8080');

    // Send join message after successful connection
    socketRef.current.onopen = () => {
      console.log('Connected to WebSocket server');
      const joinMessage = { type: 'join', username, room };
      socketRef.current.send(JSON.stringify(joinMessage));
    };

    // Handle incoming messages
    socketRef.current.onmessage = (event) => {
      const message = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, message]);
    };

    // Handle WebSocket connection close
    socketRef.current.onclose = () => {
      console.log('Disconnected from WebSocket server');
    };

    // Clean up WebSocket connection on component unmount
    return () => {
      socketRef.current.close();
    };
  }, [username, room]);

  // Send message handler
  const handleSendMessage = () => {
    if (inputMessage.trim() && socketRef.current) {
      const message = { type: 'message', text: inputMessage, username, room };
      socketRef.current.send(JSON.stringify(message));
      setInputMessage(''); // Clear the input field
    }
  };

  // Auto-scroll to the latest message
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  // Typing indicator handler
  const handleTyping = () => {
    if (!isTyping) {
      setIsTyping(true);
      socketRef.current.send(JSON.stringify({ type: 'typing', username, room }));
    }
    clearTimeout(typingTimeout);
    typingTimeout = setTimeout(() => setIsTyping(false), 1000);
  };

  // Inline styles
  const styles = {
    appContainer: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      height: '100vh',
      backgroundColor: '#f0f2f5',
    },
    chatContainer: {
      width: '100%',
      maxWidth: '500px',
      backgroundColor: '#fff',
      borderRadius: '8px',
      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
      overflow: 'hidden',
    },
    chatHeader: {
      padding: '16px',
      backgroundColor: '#4a76a8',
      color: '#fff',
      textAlign: 'center',
      fontSize: '1.2em',
    },
    chatMessages: {
      padding: '10px',
      height: '300px',
      overflowY: 'auto',
      backgroundColor: '#e9ecef',
    },
    message: {
      marginBottom: '10px',
      padding: '8px',
      borderRadius: '5px',
      maxWidth: '80%',
    },
    userMessage: {
      backgroundColor: '#d1e7dd',
      alignSelf: 'flex-start',
    },
    systemMessage: {
      backgroundColor: '#f8d7da',
      color: '#842029',
      fontStyle: 'italic',
      textAlign: 'center',
    },
    typingIndicator: {
      fontStyle: 'italic',
      color: '#888',
      fontSize: '0.9em',
      textAlign: 'center',
    },
    chatInputContainer: {
      display: 'flex',
      padding: '10px',
      backgroundColor: '#fff',
      borderTop: '1px solid #ddd',
    },
    chatInput: {
      flex: 1,
      padding: '8px',
      border: '1px solid #ddd',
      borderRadius: '4px',
      marginRight: '8px',
    },
    chatButton: {
      padding: '8px 16px',
      backgroundColor: '#4a76a8',
      color: '#fff',
      border: 'none',
      borderRadius: '4px',
      cursor: 'pointer',
    },
    chatButtonHover: {
      backgroundColor: '#3a5a85',
    },
  };

  return (
    <div style={styles.appContainer}>
      <div style={styles.chatContainer}>
        <div style={styles.chatHeader}>
          Room: {room}
        </div>
        <div style={styles.chatMessages}>
          {messages.map((msg, index) => (
            <div
              key={index}
              style={{
                ...styles.message,
                ...(msg.type === 'system' ? styles.systemMessage : styles.userMessage),
              }}
            >
              {msg.type === 'system' ? (
                msg.text
              ) : (
                <span>
                  <strong>{msg.username}:</strong> {msg.text}
                </span>
              )}
            </div>
          ))}
          {isTyping && <div style={styles.typingIndicator}>Someone is typing...</div>}
          <div ref={messagesEndRef} />
        </div>
        <div style={styles.chatInputContainer}>
          <input
            type="text"
            style={styles.chatInput}
            value={inputMessage}
            onChange={(e) => {
              setInputMessage(e.target.value);
              handleTyping(); // Trigger typing indicator
            }}
            placeholder="Type your message here"
          />
          <button
            onClick={handleSendMessage}
            style={styles.chatButton}
            onMouseOver={(e) => (e.target.style.backgroundColor = styles.chatButtonHover.backgroundColor)}
            onMouseOut={(e) => (e.target.style.backgroundColor = styles.chatButton.backgroundColor)}
          >
            Send
          </button>
        </div>
      </div>
    </div>
  );
}

export default ChatPage;


//final code//
//chatpage done