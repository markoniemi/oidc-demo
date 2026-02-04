import * as dotenv from "dotenv";
import {user1} from "../users";
import fetchMock from "fetch-mock";
import "isomorphic-fetch";
import {configure, screen} from "@testing-library/react";
import EditUserPage from "../pages/EditUserPage";
import {setLocation} from "../RouterMock";
import LoginPage from "../pages/LoginPage";
import {afterEach, assert, beforeEach, describe, expect, test, vi} from "vitest";
import Role from "../../src/domain/Role.ts";
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

describe("EditUser component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
        dotenv.config({path: ".env"});
        fetchMock.mockGlobal();
        Jwt.setToken("token");
    });
    afterEach(() => {
        fetchMock.hardReset();
        Jwt.clearToken();
    });
    test("renders a user", async () => {
        fetchMock.getOnce("/api/rest/users/1", user1);
        setLocation("/users/1");
        await EditUserPage.render();
        await EditUserPage.assertUser(user1.id, user1.username, user1.email, user1.role);
    });
    test("shows an error", async () => {
        fetchMock.getOnce("/api/rest/users/1", 401);
        setLocation("/users/1");
        await EditUserPage.render();
        assert.isNotNull(await screen.getByText("Error loading user"));
    });
    test("shows validation error with empty user", async () => {
        fetchMock.getOnce("/api/rest/users/1", {id:1, username: "", password: "", email: "", role: "" });
        setLocation("/users/1");
        await EditUserPage.render();
        await EditUserPage.assertUser(1, "", "", undefined);
        await EditUserPage.clickSaveUser();
        assert.isNotNull(await screen.getByText("Username required"));
        assert.isNotNull(await screen.getByText("Password required"));
        assert.isNotNull(await screen.getByText("Email required"));
        assert.isNotNull(await screen.getByText("Role required"));
    });
    test("shows an error with invalid user", async () => {
        setLocation("/users/new");
        await EditUserPage.render();
        fetchMock.postOnce("/api/rest/users", 404);
        await EditUserPage.setUser("invalid", "invalid", "invalid", "ROLE_ADMIN");
        await EditUserPage.clickSaveUser();
        assert.isNotNull(await screen.getByText("Error saving user"));
    });
    test("adds a user", async () => {
        setLocation("/users/new");
        await EditUserPage.render();
        await EditUserPage.assertUser(undefined, "", "", undefined);
        await EditUserPage.setUser("user", "password", "email", "ROLE_USER");
        fetchMock.postOnce("/api/rest/users", {username: "user", email: "email"});
        await EditUserPage.clickSaveUser();
        expect(navigate).toBeCalledWith("/users");
        // expect(history.push).toBeCalledWith("/users");
        assert.isTrue(fetchMock.callHistory.done());
    });
    test("edits a user", async () => {
        fetchMock.getOnce("/api/rest/users/1", user1);
        setLocation("/users/1");
        await LoginPage.render();
        await EditUserPage.assertUser(user1.id, user1.username, user1.email, user1.role);
        await EditUserPage.setUser("newUsername", "newPassword", "newEmail", "ROLE_USER");
        await EditUserPage.assertUser(1, "newUsername", "newEmail", Role.ROLE_USER);
        fetchMock.putOnce("/api/rest/users/1", {username: "newUsername", email: "newEmail"});
        await EditUserPage.pressEnter();
        expect(navigate).toBeCalledWith("/users");
        // expect(history.push).toBeCalledWith("/users");
        assert.isTrue(fetchMock.callHistory.done());
    });
});
