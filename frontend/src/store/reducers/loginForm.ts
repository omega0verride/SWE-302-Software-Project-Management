import { createSlice } from '@reduxjs/toolkit'

const formData = createSlice({
    name: "loginForm",
    initialState: {
        name: null,
        surname: null,
        email: null,
        password: null,
        phoneNumber: null,
        username: null
    },
    reducers: {
        setFormData: (state, action) => {
            state.name = action.payload.dataToBeSubmitted.name
            state.surname = action.payload.dataToBeSubmitted.surname
            state.email = action.payload.dataToBeSubmitted.email
            state.password = action.payload.dataToBeSubmitted.password
            state.phoneNumber = action.payload.dataToBeSubmitted.phoneNumber
            state.username = action.payload.dataToBeSubmitted.username
        },
        setFormDataAfterLogin: (state) => {
            state.password = null
        },
    }
})

export const { setFormData, setFormDataAfterLogin } = formData.actions

export default formData.reducer;