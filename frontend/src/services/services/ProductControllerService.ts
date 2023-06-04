/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateProductDTO } from '../models/CreateProductDTO';
import type { GetProductDTO } from '../models/GetProductDTO';
import type { PageResponseGetMinimalProductDTO } from '../models/PageResponseGetMinimalProductDTO';
import type { PageResponseGetModerateProductDTO } from '../models/PageResponseGetModerateProductDTO';
import type { UpdateProductDTO } from '../models/UpdateProductDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ProductControllerService {

    /**
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param range
     * @param title
     * @param used
     * @param thumbnailFilename
     * @param instagramPostUrl
     * @param id
     * @param stock
     * @param updatedAt
     * @param visible
     * @param facebookPostUrl
     * @param authorization
     * @param discount
     * @param createdAt
     * @param searchQuery
     * @param price
     * @param description
     * @returns PageResponseGetModerateProductDTO OK
     * @throws ApiError
     */
    public static getAllProducts(
        pageSize: number = 30,
        pageNumber?: number,
        sortBy?: string,
        range?: string,
        title?: string,
        used?: string,
        thumbnailFilename?: string,
        instagramPostUrl?: string,
        id?: string,
        stock?: string,
        updatedAt?: string,
        visible?: string,
        facebookPostUrl?: string,
        authorization?: string,
        discount?: string,
        createdAt?: string,
        searchQuery?: string,
        price?: string,
        description?: string,
    ): CancelablePromise<PageResponseGetModerateProductDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/products',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'pageSize': pageSize,
                'pageNumber': pageNumber,
                'sortBy': sortBy,
                'range': range,
                'title': title,
                'used': used,
                'thumbnailFilename': thumbnailFilename,
                'instagramPostURL': instagramPostUrl,
                'id': id,
                'stock': stock,
                'updatedAt': updatedAt,
                'visible': visible,
                'facebookPostURL': facebookPostUrl,
                'discount': discount,
                'createdAt': createdAt,
                'searchQuery': searchQuery,
                'price': price,
                'description': description,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static createProduct(
        requestBody: CreateProductDTO,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/products',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param isThumbnail
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static uploadImage(
        productId: number,
        isThumbnail: boolean = false,
        requestBody?: {
            file: Blob;
        },
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/products/uploadImage/{productId}',
            path: {
                'productId': productId,
            },
            query: {
                'isThumbnail': isThumbnail,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param authorization
     * @returns GetProductDTO OK
     * @throws ApiError
     */
    public static getProductById(
        productId: number,
        authorization?: string,
    ): CancelablePromise<GetProductDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/products/{productId}',
            path: {
                'productId': productId,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @returns any OK
     * @throws ApiError
     */
    public static deleteProduct(
        productId: number,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/products/{productId}',
            path: {
                'productId': productId,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static updateProduct(
        productId: number,
        requestBody: UpdateProductDTO,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static setCustomFields(
        productId: number,
        requestBody: Record<string, any>,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}/setCustomFields',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static setCustomFields1(
        productId: number,
        requestBody: Array<{
            value?: any;
            key?: string;
        }>,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}/setCustomFieldsFromList',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static removeCustomFields(
        productId: number,
        requestBody: Array<string>,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}/removeCustomFields',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static removeCustomField(
        productId: number,
        requestBody: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}/removeCustomField',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param productId
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static addCustomFields(
        productId: number,
        requestBody: Record<string, any>,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/products/{productId}/addCustomFields',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param range
     * @param title
     * @param used
     * @param thumbnailFilename
     * @param instagramPostUrl
     * @param id
     * @param stock
     * @param updatedAt
     * @param visible
     * @param facebookPostUrl
     * @param authorization
     * @param discount
     * @param createdAt
     * @param searchQuery
     * @param price
     * @param description
     * @returns PageResponseGetMinimalProductDTO OK
     * @throws ApiError
     */
    public static getProductsSearchSuggestion(
        pageSize: number = 30,
        pageNumber?: number,
        sortBy?: string,
        range?: string,
        title?: string,
        used?: string,
        thumbnailFilename?: string,
        instagramPostUrl?: string,
        id?: string,
        stock?: string,
        updatedAt?: string,
        visible?: string,
        facebookPostUrl?: string,
        authorization?: string,
        discount?: string,
        createdAt?: string,
        searchQuery?: string,
        price?: string,
        description?: string,
    ): CancelablePromise<PageResponseGetMinimalProductDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/products/searchSuggestions',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'pageSize': pageSize,
                'pageNumber': pageNumber,
                'sortBy': sortBy,
                'range': range,
                'title': title,
                'used': used,
                'thumbnailFilename': thumbnailFilename,
                'instagramPostURL': instagramPostUrl,
                'id': id,
                'stock': stock,
                'updatedAt': updatedAt,
                'visible': visible,
                'facebookPostURL': facebookPostUrl,
                'discount': discount,
                'createdAt': createdAt,
                'searchQuery': searchQuery,
                'price': price,
                'description': description,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param filePath
     * @returns any OK
     * @throws ApiError
     */
    public static deleteImage(
        filePath: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/products/deleteImage',
            query: {
                'filePath': filePath,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
