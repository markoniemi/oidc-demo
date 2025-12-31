import {AuthProvider} from "react-oidc-context";
import OidcService from "../api/OidcService";
import Content from "./Content";

export default function App() {
    const onSigninCallback = async (): Promise<void> => {
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    return (
        <AuthProvider {...OidcService.oidcConfig} onSigninCallback={onSigninCallback}>
            <Content/>
        </AuthProvider>
    );
}
