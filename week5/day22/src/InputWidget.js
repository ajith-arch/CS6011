// src/InputWidget.js
import React from 'react';

function InputWidget({ label, placeholder, value, onChange }) {
  return (
    <div>
      <label>{label}</label>
      <input
        type="text"
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}

export default InputWidget;

//process
//namees