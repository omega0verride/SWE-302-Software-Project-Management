import { checkTokenExpiration } from '../store/localStorage/refreshToken'

export const getUsers = async () => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/users`, {
      method: 'GET',
      headers: new Headers({
        Authorization: `Bearer ${access_token}`,
      }),
    })

    const { items } = await response.json()
    return items
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const createUser = async (
  data: object,
  admin: boolean = false,
  enabled: boolean = false,
) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/users/register?isAdmin=${admin}&skipVerification=${enabled}`,
      {
        method: 'POST',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
          'Content-Type': 'application/json',
        }),
        body: JSON.stringify(data),
      },
    )
    console.log(response)
    const responseResolved = await response.json()
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const deleteUser = async (username: string) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/users/${username}`,
      {
        method: 'DELETE',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
        }),
      },
    )

    const responseResolved = await response.json()
    console.log(responseResolved)
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const updateUser = async (username: string, data: object) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/users/${username}`,
      {
        method: 'PATCH',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
          'Content-Type': 'application/json',
        }),
        body: JSON.stringify(data),
      },
    )

    console.log(response)
    const responseResolved = await response.json()
    console.log(responseResolved)
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const authorities = ['Admin', 'Basic User']

export const userStatus = ['Enabled', 'Disabled']
