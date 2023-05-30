import React from 'react'

interface myProps {
  placeholder: string
  dataToBeSubmitted: object
  typeOfField: string
}

const UsernameTextField = (props: myProps) => {
  const { placeholder, dataToBeSubmitted, typeOfField } = props
  const handleChange = (event: any) => {
    dataToBeSubmitted[typeOfField] = event.target.value
  }

  return (
    <div>
      <input
        type='text'
        placeholder={placeholder}
        style={{
          borderColor: '#BEBEBF',
          borderWidth: '2px',
          borderStyle: 'solid',
          borderRadius: '8px',
          width: '97.2%',
          fontSize: '14px',
          padding: '10px',
          marginBottom: '10px'
        }}
        onChange={handleChange}
      />
    </div>
  )
}

export default UsernameTextField
