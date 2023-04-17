import React from "react"
import Image from 'next/image'
import logopic from '../../public/dummyLogo.jpg'

//Incomplete
//I had some issues with image importing which I will try fixing at a later date
const PageLogo = () => {
    return(
        // style={{position:'relative', left:'1%', top:'0.1rem'}}
        <div >
            <Image 
                src={logopic} 
                alt="user" 
                style={{ objectFit: "cover", borderRadius: "50%" }} 
                width={82}
                height={82}
            />
        </div>
    )
}

export default PageLogo