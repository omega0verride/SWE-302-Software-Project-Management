import router from 'next/router'
import { getFromStorage, saveToStorage } from './manageStorage'

const refreshTokenFunction = async (refresh_token: string) => {
  try {
    console.log({ refresh_token })
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/token/refresh`,
      {
        method: 'POST',
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
  const expires_at: string = getFromStorage('expires_at')!
  const refresh_token: string = getFromStorage('refresh_token')!
  const access_token: string = getFromStorage('access_token')!
  const expiredToInt = parseInt(expires_at)
  // console.log({ expiredToInt, date: Date.now() / 1000 })
  // console.log(expiredToInt < Date.now() / 1000)
  const response = await refreshTokenFunction(refresh_token)
  console.log(response)
  if (expiredTo < Date.now() / 1000) {
    console.log('INSIDE')
    // localStorage.clear()
    // const response = await refreshTokenFunction(refresh_token)
    // saveToStorage('access_token', response?.access_token)
    // saveToStorage('refresh_token', response?.refresh_token)
    // saveToStorage('expires_at', response?.expires_at)
    // saveToStorage('username', response?.username)
    // return response?.access_token
    router.push('/login')
  } else {
    return access_token
  }
}
