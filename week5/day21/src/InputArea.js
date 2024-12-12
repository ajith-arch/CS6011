import React, { useRef } from 'react';

function InputArea( {onAdd} ) {
  // Use state to manage the input value
  const inputTextArea = useRef(null);

  // Handler to add the item
  const handleAddItem = () => {
    const newItem = inputTextArea.current.value;
    if (newItem) {
      onAdd(newItem);
      inputTextArea.current.value = '';
    }
  };

  // Inline styles
  const inputAreaStyles = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    marginTop: '10px',
  };

  const textAreaStyles = {
    width: '200px',
    height: '50px',
    marginBottom: '10px',
  };

  const buttonStyles = {
    padding: '5px 15px',
    fontSize: '16px',
    cursor: 'pointer',
  };

  return (
    <div style={inputAreaStyles}>
      <textarea ref={inputTextArea} placeholder="New task" style={textAreaStyles}/>
      <button onClick={handleAddItem} style={buttonStyles}>Add</button>
    </div>
  );
}

export default InputArea;