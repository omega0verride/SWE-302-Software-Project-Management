import {useDispatch, useSelector} from 'react-redux'
import { RootState } from '../store/store'
import React, { useEffect } from 'react'
import LoginButton from '../components/loginButton'
import UsernameTextField from '../components/UsernameTextField'

export default function Home() {

  const user = useSelector((state: RootState)=>state.user)

  const dispatch = useDispatch()

  useEffect(()=>{}, [user]);

  return (
      <div>
          <LoginButton></LoginButton>
          <UsernameTextField></UsernameTextField>
      </div>
  )
}
