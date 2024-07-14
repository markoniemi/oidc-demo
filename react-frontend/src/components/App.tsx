import React from "react";
import { AuthProvider } from "react-oidc-context";
import Empty from "../domain/Empty";
import { oidcConfig } from "../api/OidcService";
import Content from "./Content";

class App extends React.Component<Empty, Empty> {
    public override render(): React.ReactNode {
        return (
            <AuthProvider {...oidcConfig} onSigninCallback={onSigninCallback}>
                <Content />
            </AuthProvider>
        );
    }
}

export default App;

async function onSigninCallback(): Promise<void> {
    window.history.replaceState({}, document.title, window.location.pathname);
}
