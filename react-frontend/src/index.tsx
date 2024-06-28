import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import App from "./components/App";
import "./main.css";
import { AuthProvider } from "react-oidc-context";
import { oidcConfig } from "./api/OidcService";

const render = (): void => {
    ReactDOM.render(
        <AuthProvider {...oidcConfig} onSigninCallback={onSigninCallback}>
            <App />
        </AuthProvider>,
        document.getElementById("root"),
    );
};

render();

if (module.hot) {
    module.hot.accept("./components/App", () => {
        render();
    });
}

async function onSigninCallback(): Promise<void> {
    window.history.replaceState({}, document.title, window.location.pathname);
}
