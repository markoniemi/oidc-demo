import {OidcClientSettings, User} from "oidc-client-ts";

export default class OidcService {
    public static getUser() {
        const oidcStorage = sessionStorage.getItem(`oidc.user:${oidcConfig.authority}:${oidcConfig.client_id}`);
        return oidcStorage ? User.fromStorageString(oidcStorage) : null;
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
        return OidcService.getUser()?.id_token;
    }
}
// TODO move this inside class
export const oidcConfig: OidcClientSettings = {
    authority: "http://localhost:9090/realms/oidc-demo",
    client_id: "oidc-demo",
    redirect_uri: "http://localhost:8081",
    client_secret: "GJ0nlBrzNvRSbDU7Ij71hQ5xJgd7449x",
};
