// import OidcService from "./OidcService.ts";
import Jwt from "./Jwt.ts";
import OidcService from "./OidcService.ts";

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

    private static createRequest(method: string, body?: BodyInit): RequestInit {
        return {
            method: method,
            headers: this.getHeaders(),
            body: body,
        };
    }
    private static getHeaders(): Headers {
        if (Jwt.isAuthenticated()){
            return Jwt.getHeaders();
        }
        return OidcService.getHeaders();
    }
}
