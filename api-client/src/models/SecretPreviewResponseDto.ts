/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface SecretPreviewResponseDto
 */
export interface SecretPreviewResponseDto {
    /**
     * 
     * @type {string}
     * @memberof SecretPreviewResponseDto
     */
    id: string;
    /**
     * 
     * @type {Date}
     * @memberof SecretPreviewResponseDto
     */
    expiresAt: Date;
}

/**
 * Check if a given object implements the SecretPreviewResponseDto interface.
 */
export function instanceOfSecretPreviewResponseDto(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "id" in value;
    isInstance = isInstance && "expiresAt" in value;

    return isInstance;
}

export function SecretPreviewResponseDtoFromJSON(json: any): SecretPreviewResponseDto {
    return SecretPreviewResponseDtoFromJSONTyped(json, false);
}

export function SecretPreviewResponseDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): SecretPreviewResponseDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'id': json['id'],
        'expiresAt': (new Date(json['expiresAt'])),
    };
}

export function SecretPreviewResponseDtoToJSON(value?: SecretPreviewResponseDto | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'id': value.id,
        'expiresAt': (value.expiresAt.toISOString()),
    };
}
