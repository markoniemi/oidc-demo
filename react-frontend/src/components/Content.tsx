import {useAuth} from "react-oidc-context";
import UserContainer from "./UsersContainer";
import EditUser from "./EditUser";
import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {IntlProvider} from "react-intl";
import i18nConfig from "../messages/messages";
import LoginForm from "./LoginForm";
import Jwt from "../api/Jwt";

export default function Content() {
    const auth = useAuth();
    if (auth.isAuthenticated || Jwt.isAuthenticated()) {
        return (
            <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Navigate to="/users"/>}/>
                        <Route path="/users" element={<UserContainer/>}/>
                        <Route path="/users/new" element={<EditUser/>}/>
                        <Route path="/users/:id" element={<EditUser/>}/>
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
    return (
        <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
            <BrowserRouter>
                <Routes>
                    <Route path="*" element={<Navigate to="/login"/>}/>
                    <Route path="/login" element={<LoginForm/>}/>
                </Routes>
            </BrowserRouter>
        </IntlProvider>
    );
}
