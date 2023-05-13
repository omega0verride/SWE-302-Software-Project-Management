import React from 'react'

const TextFieldLabel = (props: myProps) => {
  const { text } = props

  return (
    <div>
      <h2
        style={{
          fontFamily: 'Red Hat Display',
          fontSize: '14px',
          fontWeight: '400',
          color: '#5A5A5D'
        }}
      >
        {text}
      </h2>
    </div>
  )
}

interface myProps {
  text: string
}

export default TextFieldLabel
