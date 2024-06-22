import { User } from "oidc-client-ts";

export default class OidcService {
    public static getUser() {
        const oidcStorage = localStorage.getItem(`oidc.user:<your authority>:<your client id>`);
        if (!oidcStorage) {
            return null;
        }
        return User.fromStorageString(oidcStorage);
    }
    public static getHeaders(): Headers {
        const headers = new Headers({ "content-type": "application/json" });
        const token = OidcService.getToken();
        // this.debug(`setting jwt to header: ${jwtToken}`);
        if (token) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        return headers;
    }
    public static getToken(): string {
        const user = OidcService.getUser();
        return user?.access_token;
    }
}