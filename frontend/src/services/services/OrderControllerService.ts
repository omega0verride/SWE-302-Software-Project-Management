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
): CancelablePromise<PageResponseGetOrderDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/orders',
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
     * @returns any OK
     * @throws ApiError
     */
    public static createOrder(
requestBody: CreateOrderDTO,
skipVerification: boolean = false,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/orders',
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
     * @returns any OK
     * @throws ApiError
     */
    public static changeOrderStatus(
orderId: number,
orderStatus: 'NEW' | 'KONFIRMUAR' | 'NË DËRGIM' | 'REFUZUAR' | 'MBYLLUR',
adminNotesOnStatusChange?: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/orders/{orderId}/changeStatus/{orderStatus}',
            path: {
                'orderId': orderId,
                'orderStatus': orderStatus,
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
     * @returns any OK
     * @throws ApiError
     */
    public static confirmOrder(
orderConfirmationToken: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/orders/confirm/{orderConfirmationToken}',
            path: {
                'orderConfirmationToken': orderConfirmationToken,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

    /**
     * @param orderId 
     * @returns GetOrderDTO OK
     * @throws ApiError
     */
    public static getOrderById(
orderId: number,
): CancelablePromise<GetOrderDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/orders/{orderId}',
            path: {
                'orderId': orderId,
            },
            errors: {
                415: `Unsupported Media Type`,
            },
        });
    }

}
