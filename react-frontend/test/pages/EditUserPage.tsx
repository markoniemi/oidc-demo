import AbstractPage from "./AbstractPage";
import {act, fireEvent, screen} from "@testing-library/react";
import {sleep} from "../time";
import {assert} from "vitest";
import type Role from "../../src/domain/Role.ts";
import userEvent from "@testing-library/user-event";

export default class EditUserPage extends AbstractPage {
    public static async assertPageLoaded() {
        assert.isNotNull(await this.findById("EditUser"));
    }

    public static async assertUser(id?: number, username?: string, email?: string, role?: Role): Promise<void> {
        assert.equal(await this.getValueById("id"), id == undefined ? "" : id);
        assert.equal(await this.getValueById("username"), username);
        assert.equal(await this.getValueById("email"), email);
        assert.equal(await this.getValueById("role"), role == undefined ? "" : role);
    }

    public static async setUser(username: string, password: string, email: string, role: string): Promise<void> {
        await act(async () => {
            await this.setText("username", username);
            await this.setText("password", password);
            await this.setText("email", email);
            await this.selectOption("role", role);
        });
    }

    public static async pressEnter(): Promise<void> {
        await act(async () => {
            await userEvent.type(await screen.findByTestId("email"), `[Enter]`);
            await sleep(100);
        });
    }

    public static async clickSaveUser(): Promise<void> {
        await act(async () => {
            fireEvent.click(await AbstractPage.findButton("saveUser"));
            await sleep(100);
        });
    }
}
