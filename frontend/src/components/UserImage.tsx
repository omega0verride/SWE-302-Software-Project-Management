import { Box } from "@mui/material"
import React from "react"
import Image from 'next/image'


const UserImage = (props: { image: any; size: any }) => {
    const {image, size} = props

    return (
        <Box width={size} height={size}>
            <Image 
                src={`http://localhost:3001/assets/${image}`} 
                alt="user" 
                style={{ objectFit: "cover", borderRadius: "50%" }} 
                width={size}
                height={size}
            />
        </Box>
    )
}

export default UserImage