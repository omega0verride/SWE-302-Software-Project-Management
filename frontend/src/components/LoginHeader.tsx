import React from "react"
import PageLogo from "./PageLogo"
import ShopName from "./ShopName"

const Header = () => {
    return(
        <div style={{
            height: '100px',
            width: '100vw',
            backgroundColor: 'white',
            position: "fixed",
            top: 0,
            left: 0,
            right: 0,
            paddingLeft: 42,
            paddingTop: 25,
            paddingBottom: 25,
            display: "flex",
            flexDirection: 'row',
            alignItems: "center",
            }}>
                <PageLogo></PageLogo>
                <ShopName></ShopName>
          </div>
      
    )
}

export default Header