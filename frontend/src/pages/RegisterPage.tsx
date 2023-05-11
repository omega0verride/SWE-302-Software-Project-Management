import React from "react"
import FormTitle from "../components/FormTitle"
import Footer from "../components/LoginFooter"
import LoginFooter from "../components/LoginFooter"
import Header from "../components/LoginHeader"
import LoginHeader from "../components/LoginHeader"
import LoginMain from "../components/LoginMain"
import PageLogo from "../components/PageLogo"
import ShopName from "../components/ShopName"
import TextFieldLabel from "../components/TextFieldLabel"
import UsernameTextField from "../components/UsernameTextField"

const RegisterPage = () => {
    return(
      <div style={{
        display: "flex",
        flexDirection: "column",
        height: "100vh",
        margin: 0,
        padding: 0,
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
      }}>
          <Header></Header>
          <LoginMain></LoginMain>
          <Footer></Footer>
          
      </div>
        
    )
}

export default RegisterPage