/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type CreateOrderBillingDTO = {
    clientName: string;
    clientSurname: string;
    clientPhoneNumber: string;
    clientEmail: string;
    clientAddressLine1: string;
    clientNotes?: string;
    paymentOption: CreateOrderBillingDTO.paymentOption;
};

export namespace CreateOrderBillingDTO {

    export enum paymentOption {
        CASH = 'CASH',
    }


}
