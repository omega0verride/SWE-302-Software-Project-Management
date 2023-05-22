import React from 'react'
import FormTitle from './FormTitle'
import LoginButton from './LoginButton'
import TextFieldLabel from './TextFieldLabel'
import UsernameTextField from './UsernameTextField'
import bg from '../assets/icons/loginBackground.png'
import PasswordField from './PasswordField'
//This image import displays a module not found error and the page crashes

const LoginMain = ({ register }) => {
  return (
    <div
      style={{
        backgroundImage: `url(${bg.src})`,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        width: '100%',
        height: '70%',
        margin: 0,
        padding: 0,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 100
      }}
    >
      <div
        style={{
          width: '60%',
          height: '80%',
          backgroundColor: '#F4F4F4',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center'
        }}
      >
        <div style={{ width: '80%' }}>
          <FormTitle title='Create Account' />
          <TextFieldLabel text='Username' />
          <UsernameTextField placeholder='Enter username' />
          <TextFieldLabel text='Password' />
          <PasswordField placeholder='Enter password' />

          {register && (
            <>
              <TextFieldLabel text='Email' />
              <UsernameTextField placeholder='Enter email address' />
            </>
          )}

          <LoginButton register={register} />
        </div>
      </div>
    </div>
  )
}

export default LoginMain