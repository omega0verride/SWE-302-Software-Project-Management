
export const getUsers = async (access_token:string) => {
  try {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/users`,
        {
        method: 'GET',
        headers: new Headers({
          'Authorization': `Bearer ${access_token}`
        })
      }
    )

    const responseResolved = await response.json()
    return responseResolved

  } catch(err) {
    console.log("Error at api call")
    console.log(err)
  }

}

export const createUser = async (access_token:string, data: object, admin: boolean=false, enabled: boolean=false) => {
  try {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/users/register?isAdmin=${admin}&skipVerification=${enabled}`,
        {
        method: 'POST',
        headers: new Headers({
          'Authorization': `Bearer ${access_token}`,
          "Content-Type": "application/json",
        }),
        body: JSON.stringify(data)
      }
    )
    console.log(response)
    const responseResolved = await response.json()
    return responseResolved

  } catch(err) {
    console.log("Error at api call")
    console.log(err)
  }

}



export const deleteUser = async (access_token:string, username: string) => {
  try {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/users/${username}`,
        {
        method: 'DELETE',
        headers: new Headers({
          'Authorization': `Bearer ${access_token}`
        })
      }
    )


    const responseResolved = await response.json()
    console.log(responseResolved)
    return responseResolved

  } catch(err) {
    console.log("Error at api call")
    console.log(err)
  }
}


export const updateUser = async (access_token:string, username: string, data: object) => {
  try {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/users/${username}`,
        {
        method: 'PATCH',
        headers: new Headers({
          'Authorization': `Bearer ${access_token}`,
          "Content-Type": "application/json",
        }),
        body: JSON.stringify(data)
      }
    )

      console.log(response)
    const responseResolved = await response.json()
    console.log(responseResolved)
    return responseResolved

  } catch(err) {
    console.log("Error at api call")
    console.log(err)
  }
}



export const authorities = [
  'Admin',
  'Basic User'
]

export const userStatus = [
  'Enabled',
  'Disabled'
]