import React from 'react'
import { useRouter } from 'next/router'
import { useDispatch, useSelector } from 'react-redux'
import { setLogin } from '../store/reducers/user'

interface myProps {
  register: boolean
  dataToBeSubmitted: object
  setErrorMessage: any
}

const SendData = async (
  register: boolean,
  dataToBeSubmitted: object,
  router: any,
  setErrorMessage: any,
  dispatch: any
) => {
  console.log(dataToBeSubmitted)

  try {
    const apiExtension = register ? 'users/register' : 'token'
    const response = await (
      await fetch(`${process.env.NEXT_PUBLIC_API_URL}/${apiExtension}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataToBeSubmitted)
      })
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
      dispatch(
        setLogin({
          access_token: response?.access_token,
          refresh_token: response?.refresh_token,
          expires_at: response?.expires_at,
          username: response?.username
        })
      )
      router.push('/users')
    }
  } catch (err) {
    setErrorMessage('Something went wrong, try again later!')
    console.log(err)
  }
}

const LoginButton = (props: myProps) => {
  const dispatch = useDispatch()
  const router = useRouter()

  const { register, dataToBeSubmitted, setErrorMessage } = props

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
        onClick={(e: any) =>
          SendData(
            register,
            dataToBeSubmitted,
            router,
            setErrorMessage,
            dispatch
          )
        }
      >
        {register ? 'Register' : 'Log In'}
      </button>
    </div>
  )
}

export default LoginButton
