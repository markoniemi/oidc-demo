import { assert } from "chai";
import * as dotenv from "dotenv";
import { users } from "../users";
import fetchMock from "fetch-mock";
import "isomorphic-fetch";
import { configure, screen } from "@testing-library/react";
import UsersPage from "../pages/UsersPage";
import User from "../../src/domain/User";
import { navigate, setLocation } from "../RouterMock";
import sleep from "es7-sleep";


describe("UsersContainer component", () => {
    beforeEach(() => {
        configure({ testIdAttribute: "id" });
        fetchMock.restore();
        fetchMock.postOnce("/api/rest/time", "message");
        dotenv.config({ path: "config/development.env" });
    });
    afterEach(() => {
        fetchMock.restore();
    });
    test("renders userlist", async () => {
        fetchMock.getOnce("/api/rest/users/", users);
        setLocation("/users");
        await UsersPage.render();
        sleep(100);
        await UsersPage.assertUser("user1");
        await UsersPage.assertUser("user2");
    });
    test("creates no error with empty user", async () => {
        const emptyUser = new User();
        fetchMock.getOnce("/api/rest/users/", [emptyUser]);
        setLocation("/users");
        await UsersPage.render();
        await UsersPage.assertUser("", "");
    });
    test("creates no error error with empty list", async () => {
        fetchMock.getOnce("/api/rest/users/", []);
        setLocation("/users");
        await UsersPage.render();
        assert.isNotNull(await screen.getByText("Username"));
        assert.isNotNull(await screen.getByText("Email"));
    });
    test("adds user", async () => {
        fetchMock.getOnce("/api/rest/users/", users);
        setLocation("/users");
        await UsersPage.render();
        await UsersPage.clickAddUser();
        expect(navigate).toBeCalledWith("/users/new");
        // expect(history.push).toBeCalledWith("/users/new");
    });
    test("renders error message when loading users fail", async () => {
        fetchMock.getOnce("/api/rest/users/", 401);
        setLocation("/users");
        await UsersPage.render();
        assert.isNotNull(await screen.getByText("Error loading users"));
    });
    test("edits user", async () => {
        window.confirm = jest.fn();
        fetchMock.getOnce("/api/rest/users/", users);
        setLocation("/users");
        await UsersPage.render();
        await UsersPage.clickEdit("user1");
        expect(navigate).toBeCalledWith("/users/1");
    });
    test("deletes user", async () => {
        fetchMock.get("/api/rest/users/", users);
        fetchMock.deleteOnce("/api/rest/users/1", 200);
        setLocation("/users");
        await UsersPage.render();
        await UsersPage.clickDelete("user1");
        assert.isTrue(fetchMock.done());
    });
    test("logs out", async () => {
        fetchMock.getOnce("/api/rest/users/", users);
        setLocation("/users");
        await UsersPage.render();
        await UsersPage.clickLogout();
        assert.isTrue(fetchMock.done());
        expect(navigate).toBeCalledWith("/");
    });
});
