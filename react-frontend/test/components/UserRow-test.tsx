import * as dotenv from "dotenv";
import UserRow from "../../src/components/UserRow";
import User from "../../src/domain/User";
import {user1} from "../users";
import {BrowserRouter} from "react-router";
import {act, configure, fireEvent, render, screen} from "@testing-library/react";
import i18nConfig from "../../src/messages/messages";
import {IntlProvider} from "react-intl";
import {Table} from "react-bootstrap";
import AbstractPage from "../pages/AbstractPage";
import {assert, beforeEach, describe, expect, test, vi} from "vitest";

describe("UserRow component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
        dotenv.config({path: ".env"});
    });
    test("renders a user", async () => {
        await renderUserRow(user1, undefined);
        assert.equal((await screen.findByTestId("username")).textContent, "user1");
        assert.equal((await screen.findByTestId("email")).textContent, "email1");
    });
    test("creates no error with empty user", async () => {
        const emptyUser = new User("");
        await renderUserRow(emptyUser, undefined);
        assert.equal((await screen.findByTestId("username")).textContent, "");
        assert.equal((await screen.findByTestId("email")).textContent, "");
    });
    test("deletes a user", async () => {
        const deleteUser = vi.fn();
        await renderUserRow(user1, deleteUser);
        await act(async () => {
            await fireEvent.click(await AbstractPage.findButton("delete." + user1.username));
        });
        expect(deleteUser).toBeCalledWith(1);
    });
});

async function renderUserRow(user: User, deleteUser: ((id: number|undefined) => void )|undefined): Promise<void> {
    await act(async () => {
        render(
            <Table>
                <tbody>
                <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                    <BrowserRouter>
                        <UserRow user={user} deleteUser={deleteUser}/>
                    </BrowserRouter>
                </IntlProvider>
                </tbody>
            </Table>,
        );
    });
}
