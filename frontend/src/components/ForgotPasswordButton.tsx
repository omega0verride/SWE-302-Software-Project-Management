import React from 'react'
import { useRouter } from 'next/router'
import { saveToStorage } from '../store/localStorage/manageStorage'

interface myProps {
  username: string
  setErrorMessage: any
}

const SendData = async (
  username: string,
  setErrorMessage: any,
  router: any,
) => {
  try {
    const response = await (
      await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/users/resetPassword?username=${username}`,
        {
          method: 'POST',
        },
      )
    ).json()

    console.log(response)

    if (response?.exceptionId === 'InvalidValueException') {
      setErrorMessage('Email or password is wrong!')
    } else if (response?.exceptionId === 'UserAccountNotActivatedException') {
      setErrorMessage('Please check your inbox and confirm your email address!')
    } else if (response?.exceptionId === 'InvalidCredentialsException') {
      setErrorMessage('Invalid credentials!')
    } else if (response?.success === false) {
      setErrorMessage('Email is not valid!')
    } else if (response?.exceptionId === 'ResourceAlreadyExistsException') {
      setErrorMessage('User exists, please login!')
    } else if (
      response?.message?.split(':')[0] === 'Successfully CREATED User with Id'
    ) {
      setErrorMessage('Please check your inbox and confirm your email address!')
      router.push('/login')
    }

    if (response?.access_token) {
      saveToStorage('access_token', response?.access_token)
      saveToStorage('refresh_token', response?.refresh_token)
      saveToStorage('expires_at', response?.expires_at)
      saveToStorage('username', response?.username)
      router.push('/login')
    }
  } catch (err) {
    setErrorMessage('Something went wrong, try again later!')
    console.log(err)
  }
}

const ForgotPasswordButton = (props: myProps) => {
  const router = useRouter()

  const { username, setErrorMessage } = props

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
          marginTop: '2rem',
        }}
        onClick={(e: any) => SendData(username, setErrorMessage, router)}>
        Reset Password
      </button>
    </div>
  )
}

export default ForgotPasswordButton
