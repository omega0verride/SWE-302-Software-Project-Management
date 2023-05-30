import { useDispatch, useSelector } from 'react-redux'
import React, { useEffect } from 'react'
import LoginButton from '../components/LoginButton'
import UsernameTextField from '../components/UsernameTextField'
import TextFieldLabel from '../components/TextFieldLabel'
import FormTitle from '../components/FormTitle'
import PageLogo from '../components/PageLogo'
import ShopName from '../components/ShopName'
import RedBorderButton from '../components/RedBorderButton'
import Header from '../components/LoginHeader'
import LoginMain from '../components/LoginMain'
import Footer from '../components/LoginFooter'
import RegisterPage from './register'
import { useRouter } from 'next/router'

export default function Home() {
  const { access_token } = useSelector((state: any) => state.user)
  const router = useRouter()

  return (
      <div>
        <RegisterPage></RegisterPage>
      </div>
  )
}
