import { type OidcClientSettings, User } from "oidc-client-ts";

export default class OidcService {
    public static oidcConfig: OidcClientSettings = {
        authority: "http://localhost:9090/",
        client_id: "oidc-test",
        redirect_uri: `http://localhost:${process.env.PORT}`,
        client_authentication: "client_secret_basic",
        client_secret: "Uq8odAqLX59MuZfNXRwgSRPA3w4qz5TW",
    };

    public static getUser() {
        const oidcStorage = sessionStorage.getItem(`oidc.user:${this.oidcConfig.authority}:${this.oidcConfig.client_id}`);
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

    public static getToken(): string | undefined {
        return OidcService.getUser()?.id_token;
    }
}
