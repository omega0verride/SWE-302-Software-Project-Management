import React from 'react'
import LoginMain from '../components/LoginMain'
import ShopName from '../components/ShopName'
import LoginButton from '../components/LoginButton'
import FormTitle from '../components/FormTitle'
import TextFieldLabel from '../components/TextFieldLabel'
import UsernameTextField from '../components/UsernameTextField'
import RedBorderButton from '../components/RedBorderButton'
import Header from '../components/LoginHeader'
import Footer from '../components/LoginFooter'
import bg from '../assets/icons/loginBackground.png'

const Login = () => {
  return (
    <div
      style={{
        backgroundImage: bg,
        width: '100%',
        height: '100%',
      }}
    >
      <Header />
      {/* <LoginMain />
      <ShopName />
      <LoginButton />
      <FormTitle title='Create Account' />
      <TextFieldLabel text='Username' />
      <UsernameTextField placeholder='Username' />
      <RedBorderButton content='Login' /> */}
      <p>TEST</p>

      <Footer />
    </div>
  )
}

export default Login
