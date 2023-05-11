import React from "react"
import PageLogo from "./PageLogo"
import ShopName from "./ShopName"

const Footer = () => {
    return(
        <div style={{
            height: '20%',
            width: '100vw',
            backgroundColor: '#F0F0F0',
            display: "flex",
            flexDirection: 'row',
            alignItems: "center",
            justifyContent: 'space-evenly'
            }}>
                <div>
                    <h1 style={{fontFamily:"Red Hat Display", fontSize:"18px", fontWeight:"500", color:"#D12222"}}>RED SCOOTER</h1>
                    <p style={{margin: 0, padding: 0, color:'#535A56', fontFamily:'Manrope', fontSize:'10px'}}>Motorra Elektrik</p>
                    <p style={{margin: 0, padding: 0, color:'#535A56', fontFamily:'Manrope', fontSize:'10px'}}>Dedikuar pasionit dhe adrenalinës</p>
                </div>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <p style={{display:'block', marginLeft:'4px'}}>About us</p>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', display:'block', textAlign:'left'}}>About</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Products</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Promotion</button>
                </div>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <p style={{display:'block', marginLeft:'4px'}}>Explore</p>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', display:'block', textAlign:'left'}}>Career</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Privacy</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Terms & Conditions</button>
                </div>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <p style={{display:'block', marginLeft:'4px'}}>Contact</p>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', display:'block', textAlign:'left'}}>(+355) 0696867903</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Rruga Hoxha Tasim,</button>
                    <button style={{backgroundColor:"transparent", border: 'none', cursor: 'pointer', marginTop:'5px', display:'block', textAlign:'left'}}>Tiranë, Albania</button>
                </div>
          </div>
      
    )
}

export default Footer