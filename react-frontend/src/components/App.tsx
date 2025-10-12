import * as React from "react";
import UserContainer from "./UsersContainer";
import EditUser from "./EditUser";
import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {IntlProvider} from "react-intl";
import i18nConfig from "../messages/messages";
import LoginForm from "./LoginForm";
import type {Empty} from "../domain/Empty";

class App extends React.Component<Empty, Empty> {
    public override render(): React.ReactNode {
        return (
            <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Navigate to="/login"/>}/>
                        <Route path="/login" element={<LoginForm/>}/>
                        <Route path="/users" element={<UserContainer/>}/>
                        <Route path="/users/new" element={<EditUser/>}/>
                        <Route path="/users/:id" element={<EditUser/>}/>
                    </Routes>
                </BrowserRouter>
            </IntlProvider>
        );
    }
}

export default App;
