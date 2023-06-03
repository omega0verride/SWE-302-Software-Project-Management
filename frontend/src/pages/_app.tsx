import type { AppProps } from 'next/app';
import { ApolloClient, InMemoryCache, ApolloProvider, gql } from '@apollo/client';
import { wrapper } from '../store/store'
import React, { Fragment } from 'react'


const client = new ApolloClient({
  uri: 'YOUR_API_ENDPOINT', // Replace with your API endpoint
  cache: new InMemoryCache(),
});

const MyApp = ({ Component, pageProps }: AppProps) => (
  <ApolloProvider client={client}>
    <Component {...pageProps} />
  </ApolloProvider>
)

export default wrapper.withRedux(MyApp)
