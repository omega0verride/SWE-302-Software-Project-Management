import { configureStore, combineReducers } from "@reduxjs/toolkit";
import { createWrapper } from "next-redux-wrapper";
import storage from "redux-persist/lib/storage/session";
import { persistStore, persistReducer } from "redux-persist";
import userReducer from "./reducers/user"

//COMBINING ALL REDUCERS
const reducer = {
    user: userReducer
};

const rootReducer = combineReducers({
    user: userReducer
});

let store = configureStore({
	reducer,
	middleware: (getDefaultMiddleware) =>
		getDefaultMiddleware({
			serializableCheck: false,
		}),
});

const makeStore = () => {
	//If it's on client side, create a store which will persist
	const persistConfig = {
		key: "store",
		whitelist: ["user"], // only counter will be persisted, add other reducers if needed
		storage, // if needed, use a safer storage
	};
	const persistedReducer = persistReducer(persistConfig, rootReducer); // Create a new reducer with our existing reducer
	store = configureStore({
		reducer: persistedReducer,
		middleware: (getDefaultMiddleware) =>
			getDefaultMiddleware({
				serializableCheck: false,
			}),
	}); // Creating the store again
	// @ts-ignore:next-line
	store.__persistor = persistStore(store); // This creates a persistor object & push that persisted object to .__persistor, so that we can avail the persistability feature
	return store;
};

// export an assembled wrapper
// @ts-ignore:next-line
export const wrapper = createWrapper(makeStore);

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;

// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
export default store;