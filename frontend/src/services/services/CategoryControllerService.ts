/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateCategoryDTO } from '../models/CreateCategoryDTO';
import type { GetCategoryDTO } from '../models/GetCategoryDTO';
import type { UpdateCategoryDTO } from '../models/UpdateCategoryDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class CategoryControllerService {

    /**
     * @param authorization 
     * @returns GetCategoryDTO OK
     * @returns any default response
     * @throws ApiError
     */
    public static getAllCategories(
authorization?: string,
): CancelablePromise<Array<GetCategoryDTO> | any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/categories',
            headers: {
                'Authorization': authorization,
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
    public static createCategory(
requestBody: CreateCategoryDTO,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/categories',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param categoryId 
     * @param authorization 
     * @returns GetCategoryDTO OK
     * @returns any default response
     * @throws ApiError
     */
    public static getCategoryById(
categoryId: number,
authorization?: string,
): CancelablePromise<GetCategoryDTO | any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/categories/{categoryId}',
            path: {
                'categoryId': categoryId,
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
     * @param categoryId 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static deleteCategory(
categoryId: number,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/categories/{categoryId}',
            path: {
                'categoryId': categoryId,
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
     * @param categoryId 
     * @param requestBody 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static updateCategory(
categoryId: number,
requestBody: UpdateCategoryDTO,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/categories/{categoryId}',
            path: {
                'categoryId': categoryId,
            },
            headers: {
                'Authorization': authorization,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
