// src/LoginPage.js
import React, { useState } from 'react';
import InputWidget from './InputWidget';

function LoginPage({ onJoin }) {
  const [username, setUsername] = useState('');
  const [room, setRoom] = useState('');
  const [error, setError] = useState('');

  const handleJoin = () => {
    if (username && room) {
      onJoin(username, room);
    } else {
      setError('Please enter both username and room.');
    }
  };

  // Inline styles
  const styles = {
    container: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      height: '100vh',
      backgroundColor: '#f0f2f5',
    },
    formContainer: {
      width: '100%',
      maxWidth: '400px',
      padding: '20px',
      backgroundColor: '#fff',
      borderRadius: '8px',
      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
      textAlign: 'center',
    },
    title: {
      marginBottom: '20px',
      fontSize: '1.5em',
      color: '#333',
    },
    inputContainer: {
      marginBottom: '15px',
      textAlign: 'left',
    },
    label: {
      display: 'block',
      marginBottom: '5px',
      fontSize: '0.9em',
      color: '#555',
    },
    input: {
      width: '100%',
      padding: '8px',
      fontSize: '1em',
      borderRadius: '4px',
      border: '1px solid #ddd',
      outline: 'none',
    },
    button: {
      width: '100%',
      padding: '10px',
      fontSize: '1em',
      color: '#fff',
      backgroundColor: '#4a76a8',
      border: 'none',
      borderRadius: '4px',
      cursor: 'pointer',
    },
    buttonHover: {
      backgroundColor: '#3a5a85',
    },
    error: {
      color: '#ff4d4d',
      fontSize: '0.9em',
      marginBottom: '15px',
    },
  };

  return (
    <div style={styles.container}>
      <div style={styles.formContainer}>
        <h1 style={styles.title}>Login</h1>
        {error && <div style={styles.error}>{error}</div>}
        <div style={styles.inputContainer}>
          <label style={styles.label}>Name:</label>
          <input
            type="text"
            placeholder="Enter your name"
            style={styles.input}
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div style={styles.inputContainer}>
          <label style={styles.label}>Room:</label>
          <input
            type="text"
            placeholder="Enter room name"
            style={styles.input}
            value={room}
            onChange={(e) => setRoom(e.target.value)}
          />
        </div>
        <button
          onClick={handleJoin}
          style={styles.button}
          onMouseOver={(e) => (e.target.style.backgroundColor = styles.buttonHover.backgroundColor)}
          onMouseOut={(e) => (e.target.style.backgroundColor = styles.button.backgroundColor)}
        >
          Join Room
        </button>
      </div>
    </div>
  );
}

export default LoginPage;
