import React from 'react'
import Footer from '../components/LoginFooter'
import Header from '../components/LoginHeader'
import LoginMain from '../components/LoginMain'

const Login = () => {
  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        margin: 0,
        padding: 0,
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0
      }}
    >
      <Header />
      <LoginMain register={false} />
      <Footer />
    </div>
  )
}

export default Login
