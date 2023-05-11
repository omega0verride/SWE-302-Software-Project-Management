import React from "react"
import PageLogo from "./PageLogo"
import ShopName from "./ShopName"

const Header = () => {
    return(
        <div style={{
            height: '10%',
            width: '100vw',
            backgroundColor: 'white',
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