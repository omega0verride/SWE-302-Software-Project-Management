import React from "react";

interface myProps{
    content: string
}



const RedBorderButton = (props: myProps) => {
    const {content} = props;
    return(
        <div >
            <button type="button" style={{width:'104px', height:'40px', borderRadius:'4px', borderWidth:'1px', borderColor:'#D12222', borderStyle:'solid', backgroundColor:'transparent', color:'#D12222', fontFamily:'Quicksand', fontWeight:'500', fontSize:'20px'}}>{content}</button>
        </div>
    )
}


export default RedBorderButton