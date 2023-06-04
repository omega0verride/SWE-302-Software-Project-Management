export const saveToStorage = (key: string, value: any) => {
  if (typeof window !== 'undefined') {
    return window.localStorage.setItem(key, value)
  }
}

// get from storage
export const getFromStorage = (key: string) => {
  if (typeof window !== 'undefined') {
    return window.localStorage.getItem(key)
  }
}
