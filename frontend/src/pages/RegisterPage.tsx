import React from "react"
import FormTitle from "../components/FormTitle"
import PageLogo from "../components/PageLogo"
import ShopName from "../components/ShopName"
import TextFieldLabel from "../components/TextFieldLabel"
import UsernameTextField from "../components/UsernameTextField"

const RegisterPage = () => {
    return(
        <div className='main_div' style={{display:'flex',flexWrap:'wrap'}}>
      <div className='header' style={{height:"10%", width:"100%", backgroundColor:"white", display:'flex'}}>
        <PageLogo></PageLogo>
        <ShopName></ShopName>
      </div>
      <div className='body' style={{height:"70%", width:"100%", backgroundColor:"red"}}>
        <FormTitle title='Create Account'></FormTitle>
        <TextFieldLabel text='Username'></TextFieldLabel>
        <UsernameTextField placeholder='Enter Username'></UsernameTextField>
      </div>
      <div className='footer' style={{height:"20%", width:"100%", backgroundColor:"#F0F0F0"}}>
        <p>About us</p>
      </div>
    </div>
    )
}

export default RegisterPage