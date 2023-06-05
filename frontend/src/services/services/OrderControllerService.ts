/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateOrderDTO } from '../models/CreateOrderDTO';
import type { GetOrderDTO } from '../models/GetOrderDTO';
import type { PageResponseGetOrderDTO } from '../models/PageResponseGetOrderDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class OrderControllerService {

    /**
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param clientNotes
     * @param paymentOption
     * @param userId
     * @param orderLinesId
     * @param orderLinesTitle
     * @param createdAt
     * @param orderStatus
     * @param clientEmail
     * @param orderLinesDiscount
     * @param clientSurname
     * @param orderLinesUsed
     * @param id
     * @param orderLinesProductId
     * @param orderLinesRange
     * @param orderLinesQuantity
     * @param clientAddressLine1
     * @param orderLinesUpdatedAt
     * @param orderLinesCreatedAt
     * @param updatedAt
     * @param orderLinesPrice
     * @param clientPhoneNumber
     * @param orderLinesDescription
     * @param clientName
     * @param authorization JWT Bearer Authorization Header
     * @returns PageResponseGetOrderDTO OK
     * @throws ApiError
     */
    public static getAllOrders(
        pageSize: number = 30,
        pageNumber?: number,
        sortBy?: string,
        clientNotes?: string,
        paymentOption?: string,
        userId?: string,
        orderLinesId?: string,
        orderLinesTitle?: string,
        createdAt?: string,
        orderStatus?: string,
        clientEmail?: string,
        orderLinesDiscount?: string,
        clientSurname?: string,
        orderLinesUsed?: string,
        id?: string,
        orderLinesProductId?: string,
        orderLinesRange?: string,
        orderLinesQuantity?: string,
        clientAddressLine1?: string,
        orderLinesUpdatedAt?: string,
        orderLinesCreatedAt?: string,
        updatedAt?: string,
        orderLinesPrice?: string,
        clientPhoneNumber?: string,
        orderLinesDescription?: string,
        clientName?: string,
        authorization?: string,
    ): CancelablePromise<PageResponseGetOrderDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/orders',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'pageSize': pageSize,
                'pageNumber': pageNumber,
                'sortBy': sortBy,
                'clientNotes': clientNotes,
                'paymentOption': paymentOption,
                'userId': userId,
                'orderLines.id': orderLinesId,
                'orderLines.title': orderLinesTitle,
                'createdAt': createdAt,
                'orderStatus': orderStatus,
                'clientEmail': clientEmail,
                'orderLines.discount': orderLinesDiscount,
                'clientSurname': clientSurname,
                'orderLines.used': orderLinesUsed,
                'id': id,
                'orderLines.productId': orderLinesProductId,
                'orderLines.range': orderLinesRange,
                'orderLines.quantity': orderLinesQuantity,
                'clientAddressLine1': clientAddressLine1,
                'orderLines.updatedAt': orderLinesUpdatedAt,
                'orderLines.createdAt': orderLinesCreatedAt,
                'updatedAt': updatedAt,
                'orderLines.price': orderLinesPrice,
                'clientPhoneNumber': clientPhoneNumber,
                'orderLines.description': orderLinesDescription,
                'clientName': clientName,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param requestBody
     * @param skipVerification
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static createOrder(
        requestBody: CreateOrderDTO,
        skipVerification: boolean = false,
        authorization?: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/orders',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'skipVerification': skipVerification,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param orderId
     * @param orderStatus
     * @param adminNotesOnStatusChange
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static changeOrderStatus(
        orderId: number,
        orderStatus: 'NEW' | 'KONFIRMUAR' | 'NË DËRGIM' | 'REFUZUAR' | 'MBYLLUR',
        adminNotesOnStatusChange?: string,
        authorization?: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/orders/{orderId}/changeStatus/{orderStatus}',
            path: {
                'orderId': orderId,
                'orderStatus': orderStatus,
            },
            headers: {
                'Authorization': authorization,
            },
            query: {
                'adminNotesOnStatusChange': adminNotesOnStatusChange,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param orderConfirmationToken
     * @param authorization JWT Bearer Authorization Header
     * @returns any OK
     * @throws ApiError
     */
    public static confirmOrder(
        orderConfirmationToken: string,
        authorization?: string,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/orders/confirm/{orderConfirmationToken}',
            path: {
                'orderConfirmationToken': orderConfirmationToken,
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
     * @param orderId
     * @param authorization JWT Bearer Authorization Header
     * @returns GetOrderDTO OK
     * @throws ApiError
     */
    public static getOrderById(
        orderId: number,
        authorization?: string,
    ): CancelablePromise<GetOrderDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/orders/{orderId}',
            path: {
                'orderId': orderId,
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
