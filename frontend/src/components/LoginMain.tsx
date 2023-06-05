import React, { useState } from 'react'
import FormTitle from './FormTitle'
import LoginButton from './LoginButton'
import TextFieldLabel from './TextFieldLabel'
import UsernameTextField from './UsernameTextField'
import bg from '../assets/icons/loginBackground.png'
import PasswordField from './PasswordField'
import Link from 'next/link'

const LoginMain = ({ register }) => {
  const [errorMesssage, setErrorMessage] = useState('')
  const dataToBeSubmitted = register
    ? { name: '', surname: '', email: '', password: '', phoneNumber: '' }
    : { username: '', password: '' }

  const goTo = register
    ? 'Already have an account?./login'
    : "Don't have an account?./register"

  return (
    <div
      style={{
        backgroundImage: `url(${bg.src})`,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        width: '100%',
        height: '95%',
        margin: 0,
        padding: 0,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 100,
      }}>
      <div
        style={{
          width: '60%',
          height: '95%',
          backgroundColor: '#F4F4F4',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}>
        <div style={{ width: '80%' }}>
          {errorMesssage && (
            <div style={{ color: '#D12222' }}>{errorMesssage}</div>
          )}

          <FormTitle title={register ? 'Create account' : 'Login'} />
          {register && (
            <>
              <TextFieldLabel text="Name" />
              <UsernameTextField
                placeholder="Enter your name"
                dataToBeSubmitted={dataToBeSubmitted}
                typeOfField="name"
              />
              <TextFieldLabel text="Surname" />
              <UsernameTextField
                placeholder="Enter your surname"
                dataToBeSubmitted={dataToBeSubmitted}
                typeOfField="surname"
              />
              <TextFieldLabel text="Phone Number" />
              <UsernameTextField
                placeholder="Enter your phonenumber"
                dataToBeSubmitted={dataToBeSubmitted}
                typeOfField="phoneNumber"
              />
            </>
          )}

          <TextFieldLabel text="Email" />
          <UsernameTextField
            placeholder={'Enter email'}
            dataToBeSubmitted={dataToBeSubmitted}
            typeOfField={register ? 'email' : 'username'}
          />
          <TextFieldLabel text="Password" />
          <PasswordField
            placeholder="Enter password"
            dataToBeSubmitted={dataToBeSubmitted}
            typeOfField="password"
          />

          <Link href={goTo.split('.')[1]}>{goTo.split('.')[0]}</Link>
          {!register && (
            <Link
              style={{ marginTop: '1rem', display: 'block' }}
              href="/forgotPassword">
              Forgot password?
            </Link>
          )}

          <LoginButton
            register={register}
            dataToBeSubmitted={dataToBeSubmitted}
            setErrorMessage={setErrorMessage}
          />
        </div>
      </div>
    </div>
  )
}

export default LoginMain
