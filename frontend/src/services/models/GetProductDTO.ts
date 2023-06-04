/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { FileDTO } from './FileDTO';
import type { GetMinimalCategoryDTO } from './GetMinimalCategoryDTO';

export type GetProductDTO = {
    createdAt?: number;
    updatedAt?: number;
    id?: number;
    title?: string;
    description?: string;
    price?: number;
    range?: number;
    discount?: number;
    used?: boolean;
    stock?: number;
    thumbnail?: string;
    instagramPostURL?: string;
    facebookPostURL?: string;
    visible?: boolean;
    customFields?: Record<string, any>;
    categories?: Array<GetMinimalCategoryDTO>;
    images?: Array<FileDTO>;
};

