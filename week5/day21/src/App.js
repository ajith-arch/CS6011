// App.js
import React, { useState } from 'react';
import ToDoList from './ToDoList';
import InputArea from './InputArea';


function App() {
  const [items, setItems] = useState([
    { text: "Wash dishes", completed: false },
    { text: "Take out trash", completed: false },
    { text: "Do homework", completed: false }
  ]);

  // Function to add an item to the list
  const addItem = (newItem) => {
    setItems(prevItems => [...prevItems, { text: newItem, completed: false }]);
  };

  // Function to delete an item from the list
  const deleteItem = (index) => {
    setItems(prevItems => prevItems.filter((item, i) => i !== index));
  };

  const toggleComplete = (index) => {
    setItems(prevItems => 
      prevItems.map((item, i) => i === index ? { ...item, completed: !item.completed } : item)
    );
  };

  // Optional: Test function to quickly add a test item
  const testSomething = () => {
    addItem("Test Item");
  };

  // Inline CSS styles
  const appStyles = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    ustifyContent: 'center',
    fontFamily: 'Arial, sans-serif',
    height: '100vh', // Full viewport height for vertical centering
    marginTop: '50px',
  };

  const titleStyles = {
    fontSize: '2em',
    marginBottom: '20px',
  };

  const buttonStyles = {
    marginTop: '10px',
    padding: '5px 15px',
    fontSize: '18px',
    cursor: 'pointer',

  };

  return (
    <div style={appStyles}>
      <h1 style={titleStyles}>To-Do List</h1>
      <ToDoList items={items} onDelete={deleteItem} onToggleComplete={toggleComplete} />
      <InputArea onAdd={addItem} />
      <button onClick={testSomething} style={buttonStyles}>Test</button> {/* Optional test button */}
    </div>
  );
}

export default App;
