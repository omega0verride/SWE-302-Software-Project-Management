/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetOrderBillingDTO } from './GetOrderBillingDTO';
import type { GetOrderLineDTO } from './GetOrderLineDTO';

export type GetOrderDTO = {
    createdAt?: number;
    updatedAt?: number;
    id?: number;
    orderStatus?: GetOrderDTO.orderStatus;
    orderLines?: Array<GetOrderLineDTO>;
    orderBilling?: GetOrderBillingDTO;
};

export namespace GetOrderDTO {

    export enum orderStatus {
        NEW = 'NEW',
        KONFIRMUAR = 'KONFIRMUAR',
        N_D_RGIM = 'NË DËRGIM',
        REFUZUAR = 'REFUZUAR',
        MBYLLUR = 'MBYLLUR',
    }


}
