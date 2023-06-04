/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type GetOrderBillingDTO = {
    clientName?: string;
    clientSurname?: string;
    clientPhoneNumber?: string;
    clientEmail?: string;
    clientAddressLine1?: string;
    clientNotes?: string;
    paymentOption?: GetOrderBillingDTO.paymentOption;
};

export namespace GetOrderBillingDTO {

    export enum paymentOption {
        CASH = 'CASH',
    }


}
