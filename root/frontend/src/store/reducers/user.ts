import { createSlice } from '@reduxjs/toolkit'

//USER SLICE
const userSlice = createSlice({
    name: "user",

    initialState: {
        user: {
            id: undefined,
            name: undefined,
            surname: null,
            email: null,
            birthday: null,
            role: null,
            token: null
        },
    },
    reducers: {
        Login: (state, action) => {
            state.user = action.payload
        },
    }
})

export const { Login } = userSlice.actions;

export const selectUser = (state) => state.user.user;

export default userSlice.reducer;