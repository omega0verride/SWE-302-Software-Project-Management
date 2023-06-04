/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DetailedTokenDetailsDTO } from '../models/DetailedTokenDetailsDTO';
import type { MultiAuthIdentityProviderDTO } from '../models/MultiAuthIdentityProviderDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AuthTokenControllerService {

    /**
     * @param requestBody 
     * @returns DetailedTokenDetailsDTO OK
     * @throws ApiError
     */
    public static authenticate(
requestBody: MultiAuthIdentityProviderDTO,
): CancelablePromise<DetailedTokenDetailsDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/token',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @returns DetailedTokenDetailsDTO OK
     * @throws ApiError
     */
    public static refreshToken(): CancelablePromise<DetailedTokenDetailsDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/token/refresh',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
