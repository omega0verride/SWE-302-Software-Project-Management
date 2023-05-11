import React from "react"

interface myProps{
    title: string
}

const FormTitle = (props : myProps) => {
    const {title} = props
    return(
    <div>
        <h1 style={{fontFamily:"Red Hat Display", fontSize:"16px", fontWeight:"600", color:"#101012"}}>{title}</h1>
    </div>
    )
}

export default FormTitle