import * as router from "react-router";

export function setLocation(location?: string): void {
    window.history.replaceState({}, "", location);
}

export const navigate = jest.fn();
jest.spyOn(router, "useNavigate").mockImplementation(() => navigate);
