import React from "react"
import Image from 'next/image'
import logopic from '../../public/dummyLogo.jpg'
import Link from "next/link"


const PageLogo = () => {
    return (
        <div >
            <Link style={{ textDecoration: 'none', color: 'inherit' }} href={"./"}>
                <Image
                    src={logopic}
                    alt="user"
                    style={{ objectFit: "cover", borderRadius: "50%" }}
                    width={82}
                    height={82}
                />
            </Link>

        </div>
    )
}

export default PageLogo