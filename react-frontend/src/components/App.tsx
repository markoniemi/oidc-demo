import React from "react";
import { AuthContextProps, withAuth } from "react-oidc-context";
import Empty from "../domain/Empty";
import UserContainer from "./UsersContainer";
import EditUser from "./EditUser";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { IntlProvider } from "react-intl";
import i18nConfig from "../messages/messages";

class App extends React.Component<Empty, Empty> {
    public override render(): React.ReactNode {
        const auth: AuthContextProps = this.props.auth;
        if (auth.isAuthenticated) {
            return (
                <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                    <BrowserRouter>
                        <Routes>
                            <Route path="/" element={<Navigate to="/users" />} />
                            <Route path="/users" element={<UserContainer />} />
                            <Route path="/users/new" element={<EditUser />} />
                            <Route path="/users/:id" element={<EditUser />} />
                        </Routes>
                    </BrowserRouter>
                </IntlProvider>
            );
        }
        if (auth.isLoading) {
            return <div>Loading...</div>;
        }
        if (auth.error) {
            return <div>Error: {auth.error.message}</div>;
        }
        return <button onClick={() => void auth.signinRedirect()}>Log in</button>;
    }
}

export default withAuth(App);
