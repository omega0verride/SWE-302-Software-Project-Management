/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetModerateProductDTO } from './GetModerateProductDTO';

export type PageResponseGetModerateProductDTO = {
    currentPage?: number;
    totalItems?: number;
    totalPages?: number;
    items?: Array<GetModerateProductDTO>;
};
