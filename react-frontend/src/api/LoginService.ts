import type { ILoginForm } from "../components/LoginForm.tsx";

export default interface LoginService {
    login(loginForm: ILoginForm): Promise<string>;

    logout(): Promise<void>;
}
