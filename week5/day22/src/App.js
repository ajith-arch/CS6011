// src/App.js
import React, { useState } from 'react';
import LoginPage from './LoginPage';
import ChatPage from './ChatPage';

function App() {
  const [user, setUser] = useState({ username: '', room: '' });
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loading, setLoading] = useState(false);

  // Handler to join the chat
  const handleJoin = (username, room) => {
    setLoading(true);
    setTimeout(() => {
      setUser({ username, room });
      setIsLoggedIn(true);
      setLoading(false);
    }, 1000); // Simulating an API call or login process with a 1-second delay
  };

  // Handler to log out
  const handleLogout = () => {
    setIsLoggedIn(false);
    setUser({ username: '', room: '' });
  };

  return (
    <div style={{ textAlign: 'center', fontFamily: 'Arial, sans-serif' }}>
      {loading ? (
        <div>Loading...</div> // Simulate loading state during login
      ) : isLoggedIn ? (
        <div>
          <ChatPage username={user.username} room={user.room} />
          <button
            onClick={handleLogout}
            style={{ marginTop: '20px', padding: '10px 20px', cursor: 'pointer' }}
          >
            Log Out
          </button>
        </div>
      ) : (
        <LoginPage onJoin={handleJoin} />
      )}
    </div>
  );
}

export default App;


