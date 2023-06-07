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
     * @param authorization JWT Bearer Authorization Header
     * @returns PageResponseGetAppUserDTO OK
     * @returns any default response
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
authorization?: string,
): CancelablePromise<PageResponseGetAppUserDTO | any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users',
            headers: {
                'Authorization': authorization,
            },
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
     * @param requestBody 
     * @param skipVerification 
     * @param isAdmin 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static registerUser(
requestBody: CreateAppUserDTO,
skipVerification: boolean = false,
isAdmin: boolean = false,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users',
            headers: {
                'Authorization': authorization,
            },
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
     * @param username 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static resetPassword(
username: string,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/users/resetPassword',
            headers: {
                'Authorization': authorization,
            },
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
     * @param authorization JWT Bearer Authorization Header
     * @returns GetAppUserDTO OK
     * @returns any default response
     * @throws ApiError
     */
    public static getUserByUsername(
username: string,
authorization?: string,
): CancelablePromise<GetAppUserDTO | any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users/{username}',
            path: {
                'username': username,
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
     * @param username 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static deleteUserByUsername(
username: string,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/users/{username}',
            path: {
                'username': username,
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
     * @param username 
     * @param requestBody 
     * @param authorization JWT Bearer Authorization Header
     * @returns GetAppUserDTO OK
     * @returns any default response
     * @throws ApiError
     */
    public static updateUserByUsername(
username: string,
requestBody: UpdateAppUserDTO,
authorization?: string,
): CancelablePromise<GetAppUserDTO | any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/users/{username}',
            path: {
                'username': username,
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

    /**
     * @param token 
     * @param username 
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static confirmRegistration(
token: string,
username?: string,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/users/register/confirm',
            headers: {
                'Authorization': authorization,
            },
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
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static deleteUsers(
ids: Array<number>,
authorization?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/users/batchDelete/{ids}',
            path: {
                'ids': ids,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
