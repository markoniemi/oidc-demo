import { assert } from "chai";
import * as dotenv from "dotenv";
import fetchMock from "fetch-mock";
import "isomorphic-fetch";
import { configure, screen } from "@testing-library/react";
import LoginPage from "../pages/LoginPage";
import { navigate, setLocation } from "../RouterMock";

describe("LoginForm component", () => {
    beforeEach(() => {
        configure({ testIdAttribute: "id" });
        dotenv.config({ path: "config/development.env" });
        fetchMock.postOnce("/api/rest/time", "message");
        fetchMock.get("/api/rest/users/", 200);
    });
    afterEach(() => {
        fetchMock.restore();
    });
    test("opens users page with valid credentials", async () => {
        setLocation();
        await LoginPage.render();
        await LoginPage.setLogin("", "");
        fetchMock.postOnce("/api/rest/auth/login/", 200);
        await LoginPage.setLogin("user1", "user1");
        await LoginPage.assertLogin("user1", "user1");
        await LoginPage.pressEnter();
        // expect(history.push).toBeCalledWith("/users");
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
        fetchMock.postOnce("/api/rest/auth/login/", 400);
        await LoginPage.clickLogin();
        assert.isNotNull(await screen.getByText("Login error"));
    });
});
