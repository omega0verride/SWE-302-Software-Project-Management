import {useDispatch, useSelector} from 'react-redux'
import { RootState } from '../store/store'
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


export default function Home() {

  const user = useSelector((state: RootState)=>state.user)

  const dispatch = useDispatch()

  useEffect(()=>{}, [user]);

  return (
      <div style={{
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        width: '100vw',
        backgroundColor: '#F5F5F5',
        margin: 0,
        padding: 0,
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0}}>
        <div style={{
            height: '10%',
            width: '100vw',
            paddingLeft: 42,
            paddingRight: 42,
            paddingTop: 25,
            paddingBottom: 25,
            display: "flex",
            flexDirection: 'row',
            alignItems: "center",
            justifyContent: "space-between",
            }}>
                <div style={{display: "flex", flexDirection: 'row',}}>
                    <PageLogo></PageLogo>
                    <ShopName></ShopName>
                </div>
                <div style={{display: "flex", flexDirection: 'row',}}>
                </div>
                
        </div>
      </div>
  )
}
