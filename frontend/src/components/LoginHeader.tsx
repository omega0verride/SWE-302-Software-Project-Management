import React from "react"
import PageLogo from "./PageLogo"
import RedBorderButton from "./RedBorderButton"
import ShopName from "./ShopName"

const Header = () => {
    return(
        <div style={{
            height: '10%',
            width: '100vw',
            backgroundColor: 'white',
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
                
                
          </div>
      
    )
}

export default Header