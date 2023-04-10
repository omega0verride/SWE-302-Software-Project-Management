import {useDispatch, useSelector} from 'react-redux'
import { RootState } from '../store/store'
import React, { useEffect } from 'react'
import LoginButton from '../components/loginButton'
import UsernameTextField from '../components/UsernameTextField'
import TextFieldLabel from '../components/TextFieldLabel'
import FormTitle from '../components/FormTitle'
import PageLogo from '../components/PageLogo'
import ShopName from '../components/ShopName'
import RegisterPage from './RegisterPage'

export default function Home() {

  const user = useSelector((state: RootState)=>state.user)

  const dispatch = useDispatch()

  useEffect(()=>{}, [user]);

  return (
      <div>
          <PageLogo></PageLogo>
          <ShopName></ShopName>
          <LoginButton></LoginButton>
          <FormTitle title='Create Account'></FormTitle>
          <TextFieldLabel text="Username"></TextFieldLabel>
          <UsernameTextField placeholder='Username'></UsernameTextField>
      </div>
  )
}
