import React from "react"

interface myProps{
    placeholder: string
}

const UsernameTextField = ( props : myProps) => {
    const {placeholder} = props
    return(
        <div>
            <input type="text" placeholder={placeholder} style={{borderColor: "#BEBEBF", borderWidth: '2px', borderStyle: "solid", borderRadius: '8px', width:"50%", fontSize:"14px", padding:"10px", marginBottom:"10px"}}></input>
        </div>
    )
}

export default UsernameTextField