import {type ILoginForm} from "../components/LoginForm";
import Http from "./Http";
import Jwt from "./Jwt";

export default class LoginService {
    // private static readonly debug: Debug.IDebugger = Debug("LoginApi");

    public static async login(loginForm: ILoginForm): Promise<string> {
        const response: Response = await Http.post(this.getApiUrl(), JSON.stringify(loginForm));
        if (!response.ok) {
            throw new Error("error.login");
        }
        return response.text();
    }

    public static async logout(): Promise<void> {
        Jwt.clearToken();
    }

    public static getApiUrl(): string {
        return `/api/rest/auth/login/`;
    }
}
