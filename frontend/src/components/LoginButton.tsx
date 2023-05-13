import React from 'react'

const LoginButton = ({ register }) => {
  return (
    <div>
      <button
        style={{
          backgroundColor: '#D12222',
          color: 'white',
          fontSize: '16px',
          padding: '10px 20px',
          border: 'none',
          borderRadius: '8px',
          width: '100%',
          cursor: 'pointer',
          marginTop: '2rem'
        }}
      >
        {register ? 'Register' : 'Log In'}
      </button>
    </div>
  )
}

export default LoginButton
