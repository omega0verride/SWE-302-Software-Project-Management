import {useDispatch, useSelector} from 'react-redux'
import { RootState } from '../store/store'
import React, { useEffect } from 'react'
import LoginButton from '../components/loginButton'
import UsernameTextField from '../components/UsernameTextField'
import TextFieldLabel from '../components/TextFieldLabel'

export default function Home() {

  const user = useSelector((state: RootState)=>state.user)

  const dispatch = useDispatch()

  useEffect(()=>{}, [user]);

  return (
      <div>
          <LoginButton></LoginButton>
          <TextFieldLabel text="Username"></TextFieldLabel>
          <UsernameTextField placeholder='Username'></UsernameTextField>
          
      </div>
  )
}
