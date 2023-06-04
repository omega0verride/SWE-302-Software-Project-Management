/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BasicCredentialsDTO } from '../models/BasicCredentialsDTO';
import type { CreateAppUserDTO } from '../models/CreateAppUserDTO';
import type { GetAppUserDTO } from '../models/GetAppUserDTO';
import type { PageResponseGetAppUserDTO } from '../models/PageResponseGetAppUserDTO';
import type { PasswordDto } from '../models/PasswordDto';
import type { UpdateAppUserDTO } from '../models/UpdateAppUserDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AppUserControllerService {

    /**
     * @param username
     * @returns any OK
     * @throws ApiError
     */
    public static resetPassword(
        username: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users/resetPassword',
            query: {
                'username': username,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param requestBody
     * @param skipVerification
     * @param isAdmin
     * @returns any OK
     * @throws ApiError
     */
    public static registerUser(
        requestBody: CreateAppUserDTO,
        skipVerification: boolean = false,
        isAdmin: boolean = false,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users/register',
            query: {
                'skipVerification': skipVerification,
                'isAdmin': isAdmin,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param requestBody
     * @param authorization
     * @returns any OK
     * @throws ApiError
     */
    public static registerUser1(
        requestBody: BasicCredentialsDTO,
        authorization?: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users/register/resendVerification',
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

    /**
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static changePassword(
        requestBody: PasswordDto,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users/changePassword',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param username
     * @returns GetAppUserDTO OK
     * @throws ApiError
     */
    public static getUserByUsername(
        username: string,
    ): CancelablePromise<GetAppUserDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users/{username}',
            path: {
                'username': username,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param username
     * @returns any OK
     * @throws ApiError
     */
    public static deleteUserByUsername(
        username: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/users/{username}',
            path: {
                'username': username,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param username
     * @param requestBody
     * @returns GetAppUserDTO OK
     * @throws ApiError
     */
    public static updateUserByUsername(
        username: string,
        requestBody: UpdateAppUserDTO,
    ): CancelablePromise<GetAppUserDTO> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/users/{username}',
            path: {
                'username': username,
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
     * @param username
     * @param id
     * @param surname
     * @param email
     * @param userAuthType
     * @param updatedAt
     * @param enabled
     * @param createdAt
     * @param name
     * @param phoneNumber
     * @returns PageResponseGetAppUserDTO OK
     * @throws ApiError
     */
    public static getUsers(
        pageSize: number = 30,
        pageNumber?: number,
        sortBy?: string,
        username?: string,
        id?: string,
        surname?: string,
        email?: string,
        userAuthType?: string,
        updatedAt?: string,
        enabled?: string,
        createdAt?: string,
        name?: string,
        phoneNumber?: string,
    ): CancelablePromise<PageResponseGetAppUserDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users',
            query: {
                'pageSize': pageSize,
                'pageNumber': pageNumber,
                'sortBy': sortBy,
                'username': username,
                'id': id,
                'surname': surname,
                'email': email,
                'userAuthType': userAuthType,
                'updatedAt': updatedAt,
                'enabled': enabled,
                'createdAt': createdAt,
                'name': name,
                'phoneNumber': phoneNumber,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param token
     * @param username
     * @returns any OK
     * @throws ApiError
     */
    public static confirmRegistration(
        token: string,
        username?: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users/register/confirm',
            query: {
                'token': token,
                'username': username,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param ids
     * @returns any OK
     * @throws ApiError
     */
    public static deleteUsers(
        ids: Array<number>,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/users/batchDelete/{ids}',
            path: {
                'ids': ids,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
