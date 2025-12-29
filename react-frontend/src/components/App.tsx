import {AuthProvider} from "react-oidc-context";
import {oidcConfig} from "../api/OidcService";
import Content from "./Content";

export default function App() {
    const onSigninCallback = async (): Promise<void> => {
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    return (
        <AuthProvider {...oidcConfig} onSigninCallback={onSigninCallback}>
            <Content/>
        </AuthProvider>
    );
}
