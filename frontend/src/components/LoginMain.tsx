import React from 'react'
import FormTitle from './FormTitle'
import LoginButton from './LoginButton'
import TextFieldLabel from './TextFieldLabel'
import UsernameTextField from './UsernameTextField'
//import logopic from '../../public/loginBackground.jpg'

//This image import displays a module not found error and the page crashes

const LoginMain = () => {
    return(
        <div style={{backgroundColor: "#8B0000", width: "100%", height: "70%", margin: 0, padding: 0, display:'flex', alignItems: "center", justifyContent: "center",}}>
            <div style={{ width: "60%", height: "80%", backgroundColor: "#F4F4F4", display:'flex', alignItems: "center", justifyContent: "center" }}>
                <div style={{width: '80%'}}>
                    <FormTitle title="Create Account"></FormTitle>
                    <TextFieldLabel text="Username"></TextFieldLabel>
                    <UsernameTextField placeholder='Enter username'></UsernameTextField>
                    <TextFieldLabel text="Password"></TextFieldLabel>
                    <UsernameTextField placeholder='Enter password'></UsernameTextField>
                    <TextFieldLabel text="Email"></TextFieldLabel>
                    <UsernameTextField placeholder='Enter email address'></UsernameTextField>
                    <LoginButton></LoginButton>
                </div>
            </div>
        </div>
    )
}

export default LoginMain