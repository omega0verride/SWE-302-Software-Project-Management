/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type MultiAuthIdentityProviderDTO = {
    username?: string;
    password?: string;
    token?: string;
    authType?: MultiAuthIdentityProviderDTO.authType;
};

export namespace MultiAuthIdentityProviderDTO {

    export enum authType {
        BASIC = 'BASIC',
        GOOGLE = 'GOOGLE',
        FACEBOOK = 'FACEBOOK',
        INSTAGRAM = 'INSTAGRAM',
    }


}
