/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type CreateProductDTO = {
    categories?: Array<number>;
    title: string;
    description: string;
    visible?: boolean;
    price: number;
    range?: number;
    discount?: number;
    used?: boolean;
    stock?: number;
    customFields?: Record<string, any>;
    instagramPostURL?: string;
    facebookPostURL?: string;
};

