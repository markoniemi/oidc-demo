import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import App from "./components/App";
import "./main.css";
import { AuthProvider } from "react-oidc-context";
import { OidcClientSettings } from "oidc-client-ts";

const oidcConfig: OidcClientSettings = {
    authority: "https://accounts.google.com",
    client_id: "977467276012-1pkp49dcqgj8k0f3dprbl53avt8sgcep.apps.googleusercontent.com",
    redirect_uri: "http://localhost:8081",
    client_secret: "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",
};

const render = (): void => {
    ReactDOM.render(
        <AuthProvider {...oidcConfig}>
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
