// import FormData from 'form-data'

import { checkTokenExpiration } from '../store/localStorage/refreshToken'

export const uploadImage = async (formData, productId: number) => {
  try {
    const access_token: string = await checkTokenExpiration()
    const uploadImageResponse = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/products/uploadImage/${productId}`,
      {
        method: 'POST',
        headers: new Headers({
          Authorization: `Bearer ${access_token}`,
          'Content-Type': 'multipart/formdata',
        }),
        body: formData,
      },
    )
    const savedImage = await uploadImageResponse.json()
    return savedImage
  } catch (err) {
    console.log('Error at Upload API Call')
    console.log(err)
  }
}
