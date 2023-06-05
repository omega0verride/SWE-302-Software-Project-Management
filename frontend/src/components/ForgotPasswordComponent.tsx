import React, { useState } from 'react'
import FormTitle from './FormTitle'
import TextFieldLabel from './TextFieldLabel'
import bg from '../assets/icons/loginBackground.png'
import Link from 'next/link'
import ForgotPasswordButton from './forgotPasswordButton'

const ForgotPasswordComponent = () => {
  const [errorMesssage, setErrorMessage] = useState('')
  const [username, setUsername] = useState('')

  const handleChange = (event: any) => {
    setUsername(event.target.value)
  }

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

          <div
            style={{
              display: 'flex',
              height: '15rem',
              justifyContent: 'space-between',
              flexDirection: 'column',
            }}>
            <FormTitle title={'Forgot Password'} />
            <TextFieldLabel text="Email" />
            <div>
              <input
                type="text"
                placeholder={'Email'}
                style={{
                  borderColor: '#BEBEBF',
                  borderWidth: '2px',
                  borderStyle: 'solid',
                  borderRadius: '8px',
                  width: '97.2%',
                  fontSize: '14px',
                  padding: '10px',
                  marginBottom: '10px',
                }}
                onChange={handleChange}
              />
            </div>

            <Link href="/login">Log in</Link>

            <ForgotPasswordButton
              username={username}
              setErrorMessage={setErrorMessage}
            />
          </div>
        </div>
      </div>
    </div>
  )
}

export default ForgotPasswordComponent
