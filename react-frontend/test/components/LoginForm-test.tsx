import * as dotenv from "dotenv";
import fetchMock from "fetch-mock";
import "isomorphic-fetch";
import {configure, screen} from "@testing-library/react";
import LoginPage from "../pages/LoginPage";
import {setLocation} from "../RouterMock";
import {afterEach, assert, beforeEach, describe, expect, test, vi} from "vitest";
import Jwt from "../../src/api/Jwt.ts";

export const navigate = vi.fn();
vi.mock("react-router", async () => {
    const mod = await vi.importActual<typeof import("react-router")>(
        "react-router"
    );
    return {
        ...mod,
        useNavigate: () => navigate,
    };
});

describe("LoginForm component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
        dotenv.config({path: ".env"});
        fetchMock.mockGlobal();
        fetchMock.postOnce("/api/time", "message");
        fetchMock.get("/api/users/", 200);
    });
    afterEach(() => {
        fetchMock.hardReset();
        Jwt.clearToken();
    });
    test("opens users page with valid credentials", async () => {
        setLocation();
        await LoginPage.render();
        await LoginPage.setLogin("", "");
        fetchMock.postOnce("/api/auth/login/", 200);
        await LoginPage.setLogin("user1", "user1");
        await LoginPage.assertLogin("user1", "user1");
        await LoginPage.pressEnter();
        expect(navigate).toBeCalledWith("/users");
    });
    test("shows validation error with empty credentials", async () => {
        await LoginPage.render();
        await LoginPage.assertLogin("", "");
        await LoginPage.clickLogin();
        assert.isNotNull(await screen.getByText("Username required"));
        assert.isNotNull(await screen.getByText("Password required"));
    });
    test("shows an error with invalid credentials", async () => {
        await LoginPage.render();
        await LoginPage.setLogin("invalid", "invalid");
        fetchMock.postOnce("/api/auth/login/", 400);
        await LoginPage.clickLogin();
        assert.isNotNull(await screen.getByText("Login error"));
    });
});
