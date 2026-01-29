import { type OidcClientSettings, User } from "oidc-client-ts";

export default class OidcService {
  public static oidcConfig: OidcClientSettings = {
    authority: "http://localhost:9090/",
    client_id: "oidc-test",
    redirect_uri: `http://localhost:${process.env.PORT}`,
    // PKCE is enabled by default in oidc-client-ts when client_secret is omitted
  };

  public static getUser() {
    const oidcStorage = sessionStorage.getItem(`oidc.user:${this.oidcConfig.authority}:${this.oidcConfig.client_id}`);
    return oidcStorage ? User.fromStorageString(oidcStorage) : null;
  }

  public static getHeaders(): Headers {
    const headers = new Headers({ "content-type": "application/json" });
    const token = OidcService.getToken();
    if (token) {
      headers.append("Authorization", `Bearer ${token}`);
    }
    return headers;
  }

  public static getToken(): string | undefined {
    return OidcService.getUser()?.id_token;
  }

  public static clearToken(): void {
    sessionStorage.removeItem(`oidc.user:${this.oidcConfig.authority}:${this.oidcConfig.client_id}`);
  }
}
