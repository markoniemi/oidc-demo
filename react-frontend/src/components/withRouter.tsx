import { Location, NavigateFunction, Params, useLocation, useNavigate, useParams } from "react-router-dom";
import React from "react";

export interface RouterProps {
    location: Location;
    navigate: NavigateFunction;
    params: Params;
}

export interface WithRouter {
    router: RouterProps;
}

/** https://stackoverflow.com/a/78142054 */
function withRouter(Component: any) {
    function ComponentWithRouterProp(props: any) {
        const location = useLocation();
        const navigate = useNavigate();
        const params = useParams();
        return <Component {...props} router={{ location, navigate, params }} />;
    }

    return ComponentWithRouterProp;
}

export default withRouter;
