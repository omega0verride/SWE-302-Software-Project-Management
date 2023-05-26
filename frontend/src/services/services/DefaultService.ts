/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class DefaultService {

    /**
     * user login
     * user login
     * @param requestBody body login
     * @throws ApiError
     */
    public static login(
        requestBody?: {
            username: string;
            password: string;
            authType: string;
            token: string;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/token',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * user details api request
     * get user information
     * @param username user name
     * @throws ApiError
     */
    public static getUserDetails(
        username: string,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/user/users/{username}',
            path: {
                'username': username,
            },
        });
    }

    /**
     * image delete
     * delete product image
     * @param filePath
     * @throws ApiError
     */
    public static deleteImage(
        filePath: string,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/admin/products/deleteImage',
            query: {
                'filePath': filePath,
            },
        });
    }

    /**
     * updates the selected category
     * @param categoryId the category id
     * @param requestBody body update category
     * @throws ApiError
     */
    public static updateCategoryById(
        categoryId: number,
        requestBody: {
            defaultColor: string;
            textColor: string;
            name: string;
            backgroundColor: string;
            visible: boolean;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/admin/categories/{categoryId}',
            path: {
                'categoryId': categoryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * creates a new user
     * @param requestBody body create user
     * @throws ApiError
     */
    public static registerUser(
        requestBody: {
            email: string;
            name: string;
            password: string;
            phoneNumber: string;
            surname: string;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/public/users/register',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * get all the products for the seearch query
     * @param categoryId category id
     * @param pageNumber
     * @param pageSize
     * @param order
     * @param sortBy
     * @param minPrice
     * @param maxPrice
     * @param searchQuery
     * @throws ApiError
     */
    public static getSearchSuggestions(
        categoryId: number,
        pageNumber: number,
        pageSize: number,
        order: string,
        sortBy?: string,
        minPrice?: number,
        maxPrice?: number,
        searchQuery?: string,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/public/products/searchSuggestions',
            query: {
                'categoryId': categoryId,
                'pageNumber': pageNumber,
                'pageSize': pageSize,
                'sortBy': sortBy,
                'order': order,
                'minPrice': minPrice,
                'maxPrice': maxPrice,
                'searchQuery': searchQuery,
            },
        });
    }

    /**
     * creates a new product
     * @param requestBody body create a new product
     * @throws ApiError
     */
    public static createNewProduct(
        requestBody: {
            categories: any[];
            description: string;
            discount: number;
            facebookPostURL: string;
            instagramPostURL: string;
            price: number;
            stock: number;
            title: string;
            used: boolean;
            visible: boolean;
            customFields: any;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/admin/products',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * creates a new Category
     * @param requestBody body update category
     * @throws ApiError
     */
    public static createCategory(
        requestBody: {
            defaultColor: string;
            textColor: string;
            name: string;
            backgroundColor: string;
            visible: boolean;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/admin/categories',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * edit a new product
     * @param productId the category id
     * @param requestBody body edit a new product
     * @throws ApiError
     */
    public static editProductWithId(
        productId: number,
        requestBody: {
            categories: any[];
            description: string;
            discount: number;
            facebookPostURL: string;
            instagramPostURL: string;
            price: number;
            stock: number;
            title: string;
            used: boolean;
            visible: boolean;
            customFields: any;
        },
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/admin/products/{productId}',
            path: {
                'productId': productId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * deletes the selected category
     * @param categoryid the category id
     * @throws ApiError
     */
    public static deleteCategoryById(
        categoryid: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/admin/categories/{categoryid}',
            path: {
                'categoryid': categoryid,
            },
        });
    }

    /**
     * deletes the selected productId
     * @param productId the product id
     * @throws ApiError
     */
    public static deleteProductWithId(
        productId: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/admin/products/{productID}',
            path: {
                'productID': productId,
            },
        });
    }

    /**
     * get all the products for a category
     * @param categoryId category id
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param price
     * @param searchQuery
     * @throws ApiError
     */
    public static getCategoryProducts(
        categoryId: number,
        pageNumber: number,
        pageSize: number,
        sortBy?: string,
        price?: string,
        searchQuery?: string,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/public/products',
            query: {
                'categoryId': categoryId,
                'pageNumber': pageNumber,
                'pageSize': pageSize,
                'sortBy': sortBy,
                'price': price,
                'searchQuery': searchQuery,
            },
        });
    }

    /**
     * get all the categories that exist in the database
     * @throws ApiError
     */
    public static getAllCategories(): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/public/categories',
        });
    }

    /**
     * get details for only one product
     * @param productId category id
     * @throws ApiError
     */
    public static getProductDetails(
        productId: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/public/products/{productId}',
            path: {
                'productId': productId,
            },
        });
    }

    /**
     * get details for the store
     * @param storeName azal
     * @throws ApiError
     */
    public static getStoreDetailsByName(
        storeName: string,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/public/stores/name/{storeName}',
            path: {
                'storeName': storeName,
            },
        });
    }

}
