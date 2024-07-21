import Jwt from "./Jwt";
import OidcService from "./OidcService";

export default class Http {
    public static async post(url: string, body: BodyInit): Promise<Response> {
        return fetch(url, Http.createRequest("POST", body));
    }

    public static async update(url: string, body: BodyInit): Promise<Response> {
        return fetch(url, Http.createRequest("PUT", body));
    }

    public static async get(url: string, body?: BodyInit): Promise<Response> {
        return fetch(url, Http.createRequest("GET", body));
    }

    public static async delete(url: string, body?: BodyInit): Promise<Response> {
        return fetch(url, Http.createRequest("DELETE", body));
    }

    private static createRequest(method: string, body: BodyInit): RequestInit {
        return {
            method: method,
            headers: Jwt.isAuthenticated() ? Jwt.getHeaders() : OidcService.getHeaders(),
            body: body,
        };
    }
}
