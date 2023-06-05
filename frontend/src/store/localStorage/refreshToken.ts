import { getFromStorage, saveToStorage } from './manageStorage'
import jwt_decode from 'jwt-decode'

const refreshTokenFunction = async (refresh_token: string) => {
  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/token/refresh`,
      {
        method: 'GET',
        headers: new Headers({
          Authorization: `Bearer ${refresh_token}`,
        }),
      },
    )

    const res = await response.json()
    return res
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const checkTokenExpiration = async () => {
  const access_token: string = getFromStorage('access_token')!
  console.log({ access_token })
  const { exp }: { exp: number } = await jwt_decode(access_token)

  if (exp < Date.now() / 1000) {
    const refresh_token: string = getFromStorage('refresh_token')!
    const response = await refreshTokenFunction(refresh_token)
    saveToStorage('access_token', response?.access_token)
    saveToStorage('refresh_token', response?.refresh_token)
    saveToStorage('expires_at', response?.expires_at)
    saveToStorage('username', response?.username)
    return response?.access_token
  } else {
    return access_token
  }
}
