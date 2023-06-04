/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetAppUserDTO } from './GetAppUserDTO';

export type PageResponseGetAppUserDTO = {
    currentPage?: number;
    totalItems?: number;
    totalPages?: number;
    items?: Array<GetAppUserDTO>;
};

