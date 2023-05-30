import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
    name: "auth",
    initialState: {
        access_token: null,
        refresh_token: null,
        expires_at: null,
        username: null
    },
    reducers: {
        setLogin: (state, action) => {
            state.access_token = action.payload.access_token
            state.refresh_token = action.payload.refresh_token
            state.expires_at = action.payload.expires_at
            state.username = action.payload.username
        },
        setLogout: (state) => {
            state.access_token = null
            state.refresh_token = null
            state.expires_at = null
            state.username = null
        },
    }
})

export const { setLogin, setLogout } = authSlice.actions

export default authSlice.reducer;