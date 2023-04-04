import type { AppProps } from 'next/app'
import { wrapper } from '../store/store';

import React, { Fragment } from 'react';

const MyApp = ({ Component, pageProps }: AppProps) => (
  <Fragment>
    <Component {...pageProps} />
  </Fragment>
);

export default wrapper.withRedux(MyApp);