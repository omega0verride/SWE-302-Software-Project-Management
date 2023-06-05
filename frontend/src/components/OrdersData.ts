import { checkTokenExpiration } from '../store/localStorage/refreshToken'

export const getOrders = async () => {
  try {
    const access_token: string = await checkTokenExpiration()
    console.log({ access_token })
    const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/orders`, {
      method: 'GET',
      headers: new Headers({
        Authorization: `Bearer ${access_token}`,
        'Content-Type': 'application/json',
      }),
    })

    const { items } = await response.json()
    return items
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const createProduct = async (data: object) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/products`,
      {
        method: 'POST',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
          'Content-Type': 'application/json',
        }),
        body: JSON.stringify(data),
      },
    )

    const responseResolved = await response.json()
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const orderStatusToBeDelivered = {
  NEW: 'NEW',
  KONFIRMUAR: 'CONFIRMED',
  'NË DËRGIM': 'OUT_FOR_DELIVERY',
  REFUZUAR: 'CANCELED',
  MBYLLUR: 'CLOSED',
}

export const updateOrder = async (
  orderId: number,
  orderStatusReceived: string,
) => {
  try {
    const orderStatus = orderStatusToBeDelivered[orderStatusReceived]
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/orders/${orderId}/changeStatus/${orderStatus}`,
      {
        method: 'PATCH',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
          'Content-Type': 'application/json',
        }),
      },
    )

    const responseResolved = await response.json()
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const orderStatusArray = [
  'NEW',
  'KONFIRMUAR',
  'NË DËRGIM',
  'REFUZUAR',
  'MBYLLUR',
]
