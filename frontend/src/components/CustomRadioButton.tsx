import React from "react";

const CustomRadioButton = ({ label, value, checked, onChange }) => {
    const handleClick = () => {
      onChange(value);
    };
  
    return (
      <label
        style={{
          display: 'flex',
          alignItems: 'center',
          marginBottom: '5px',
          cursor: 'pointer',
        }}
      >
        <span
          style={{
            display: 'inline-block',
            width: '16px',
            height: '16px',
            marginRight: '5px',
            borderRadius: '50%',
            border: '1px solid #D12222',
            backgroundColor: checked ? '#D12222' : 'transparent',
          }}
          onClick={handleClick}
        />
        <span>{label}</span>
      </label>
    );
  };

  export default CustomRadioButton