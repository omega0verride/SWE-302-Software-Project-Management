import img from '../assets/icons/dummyLogo.jpg'
import { checkTokenExpiration } from '../store/localStorage/refreshToken'
import { Product } from './ProductsTable'
//     image: img.src,

export const getProducts = async () => {
  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/products?sortBy=-updatedAt`,
      {
        method: 'GET',
      },
    )

    const { items } = await response.json()
    const newValues = items.map((el: Product) => {
      return {
        ...el,
        thumbnail:
          el.thumbnail === 'images/no_image.jpg/no_image.jpg'
            ? img.src
            : `${process.env.NEXT_PUBLIC_IMAGE_URL}/resources/public/${el.thumbnail}`,
      }
    })
    return newValues
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
    console.log(response)
    const responseResolved = await response.json()
    return responseResolved
  } catch (err) {
    console.log('Error at api call')
    console.log(err)
  }
}

export const deleteProduct = async (productId: number) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/products/${productId}`,
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

export const updateProduct = async (productId: number, data: object) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/products/${productId}`,
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

export const productStatus = ['New', 'Used']
