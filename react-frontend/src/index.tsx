// @ts-ignore
import * as bootstrap from "bootstrap/dist/css/bootstrap.css";
// @ts-ignore
import * as main from "./main.css";
import React from "react";
import App from "./components/App";
import {createRoot} from "react-dom/client";

const root = createRoot(document.getElementById("root") as HTMLElement);
root.render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>
);
