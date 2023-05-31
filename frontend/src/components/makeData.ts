import {Person} from '../components/UsersTable'

export const GetData = async (access_token:string) => {
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

export const authorities = [
  'Admin',
  'Basic User'
]