/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CreateOrderBillingDTO } from './CreateOrderBillingDTO';
import type { CreateOrderLineDTO } from './CreateOrderLineDTO';

export type CreateOrderDTO = {
    orderLines: Array<CreateOrderLineDTO>;
    orderBilling?: CreateOrderBillingDTO;
    userId?: number;
};
