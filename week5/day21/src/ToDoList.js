import React from 'react';

function ToDoList({ items, onDelete , onToggleComplete }) {
    const containerStyles = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      };

      const itemStyles = (completed) => ({
        cursor: 'pointer',
        padding: '10px', 
        margin: '5px 0',
        border: '1px solid #ddd',
        borderRadius: '4px',
        textAlign: 'center',
        width: '200px',
        background: completed ? 'lightgreen' : 'green', 
        textDecoration: completed ? 'line-through' : 'none',
        color: completed ? 'gray' : 'white', 
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)', 
        transition: 'background 0.3s, transform 0.2s', // Smooth hover transition
        ':hover': {
          background: completed ? 'lightgray' : 'darkgreen', 
          transform: 'scale(1.02)', 
        },
      });

      return (
        <div style={containerStyles}>
          {items.map((item, index) => (
            <div key={index} style={{ display: 'flex', alignItems: 'center' }}>
              <input
                type="checkbox"
                checked={item.completed}
                onChange={() => onToggleComplete(index)}
              />
              <p onDoubleClick={() => onDelete(index)} style={itemStyles(item.completed)}>
                {item.text}
              </p>
            </div>
          ))}
        </div>
      );
    }

export default ToDoList;