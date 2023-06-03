export const saveToStorage = (key, value) => {
    if(typeof window !== 'undefined') {
        return window.localStorage.setItem(key, value);
    }
}

// get from storage
export const getFromStorage = (key) => {
    if (typeof window !== 'undefined') {
        return window.localStorage.getItem(key);
    }
}