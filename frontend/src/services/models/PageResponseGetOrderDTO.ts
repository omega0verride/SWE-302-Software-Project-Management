/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetOrderDTO } from './GetOrderDTO';

export type PageResponseGetOrderDTO = {
    currentPage?: number;
    totalItems?: number;
    totalPages?: number;
    items?: Array<GetOrderDTO>;
};

