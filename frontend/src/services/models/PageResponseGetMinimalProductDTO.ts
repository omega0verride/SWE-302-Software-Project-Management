/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetMinimalProductDTO } from './GetMinimalProductDTO';

export type PageResponseGetMinimalProductDTO = {
    currentPage?: number;
    totalItems?: number;
    totalPages?: number;
    items?: Array<GetMinimalProductDTO>;
};
