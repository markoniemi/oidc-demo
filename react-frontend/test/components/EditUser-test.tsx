import { assert } from "chai";
import * as dotenv from "dotenv";
import { user1 } from "../users";
import fetchMock from "fetch-mock";
import "isomorphic-fetch";
import { configure, screen } from "@testing-library/react";
import EditUserPage from "../pages/EditUserPage";
import { navigate, setLocation } from "../RouterMock";
import LoginPage from "../pages/LoginPage";

describe("EditUser component", () => {
    beforeEach(() => {
        configure({ testIdAttribute: "id" });
        dotenv.config({ path: "config/development.env" });
    });
    afterEach(() => {
        fetchMock.restore();
    });
    test("renders a user", async () => {
        fetchMock.getOnce("/api/rest/users/1", user1);
        setLocation("/users/1");
        await EditUserPage.render();
        await EditUserPage.assertUser(user1.id.toString(), user1.username, user1.email, user1.role.toString());
    });
    test("shows an error", async () => {
        fetchMock.getOnce("/api/rest/users/1", 401);
        setLocation("/users/1");
        await EditUserPage.render();
        assert.isNotNull(await screen.getByText("Error loading user"));
    });
    test("shows validation error with empty user", async () => {
        setLocation("/users/1");
        await EditUserPage.render();
        await EditUserPage.assertUser("", "", "", "");
        await EditUserPage.clickSaveUser();
        assert.isNotNull(await screen.getByText("Username required"));
        assert.isNotNull(await screen.getByText("Password required"));
        assert.isNotNull(await screen.getByText("Email required"));
        assert.isNotNull(await screen.getByText("Role required"));
    });
    test("shows an error with invalid user", async () => {
        setLocation("/users/new");
        await EditUserPage.render();
        fetchMock.postOnce("/api/rest/users/", 404);
        await EditUserPage.setUser("invalid", "invalid", "invalid", "ROLE_ADMIN");
        await EditUserPage.clickSaveUser();
        assert.isNotNull(await screen.getByText("Error saving user"));
    });
    test("adds a user", async () => {
        setLocation("/users/new");
        await EditUserPage.render();
        await EditUserPage.assertUser("", "", "", "");
        await EditUserPage.setUser("user", "password", "email", "ROLE_USER");
        fetchMock.postOnce("/api/rest/users/", { username: "user", email: "email" });
        await EditUserPage.clickSaveUser();
        expect(navigate).toBeCalledWith("/users");
        // expect(history.push).toBeCalledWith("/users");
        assert.isTrue(fetchMock.done());
    });
    test("edits a user", async () => {
        fetchMock.getOnce("/api/rest/users/1", user1);
        setLocation("/users/1");
        await LoginPage.render();
        await EditUserPage.assertUser(user1.id.toString(), user1.username, user1.email, user1.role.toString());
        await EditUserPage.setUser("newUsername", "newPassword", "newEmail", "ROLE_USER");
        await EditUserPage.assertUser("1", "newUsername", "newEmail", "ROLE_USER");
        fetchMock.putOnce("/api/rest/users/1", { username: "newUsername", email: "newEmail" });
        await EditUserPage.pressEnter();
        expect(navigate).toBeCalledWith("/users");
        // expect(history.push).toBeCalledWith("/users");
        assert.isTrue(fetchMock.done());
    });
});
