import {type ILoginForm} from "../components/LoginForm";
import Http from "./Http";
import Jwt from "./Jwt";
import type LoginService from "./LoginService.ts";
import OidcService from "./OidcService.ts";

export default class LoginServiceImpl implements LoginService {
    private readonly url = `/api/rest/auth/login`;

    public async login(loginForm: ILoginForm): Promise<string> {
        const response: Response = await Http.post(this.url, JSON.stringify(loginForm));
        if (!response.ok) {
            throw new Error("error.login");
        }
        return response.text();
    }

    public async logout(): Promise<void> {
        Jwt.clearToken();
        OidcService.clearToken();
    }
}
