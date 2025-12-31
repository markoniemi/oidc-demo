import {sleep} from "../time";
import {act, fireEvent, render, screen} from "@testing-library/react";
import OidcService from "../../src/api/OidcService";
import {AuthProvider} from "react-oidc-context";
import Content from "../../src/components/Content.tsx";

export default class AbstractPage {
    public static async render(): Promise<void> {
        await act(async () => {
            await render(
                <AuthProvider {...OidcService.oidcConfig} onSigninCallback={onSigninCallback}>
                    <Content/>
                </AuthProvider>,
            );
        });
        await sleep(100);
    }

    public static async getValueById(id: string): Promise<string> {
        return ((await screen.getByTestId(id)) as HTMLInputElement).value;
    }

    public static async findById(id: string): Promise<HTMLElement> {
        return (await screen.getByTestId(id)) as HTMLElement;
    }

    public static async getTextsById(id: string): Promise<(string | null)[]> {
        const elements = (await screen.getAllByTestId(id)) as HTMLInputElement[];
        return elements.map((element) => element.textContent);
    }

    public static async findButton(id: string): Promise<HTMLInputElement> {
        return (await screen.getByTestId(id)) as HTMLInputElement;
    }

    public static async setText(id: string, text: string): Promise<void> {
        fireEvent.change((await screen.getByTestId(id)) as HTMLInputElement, {target: {value: text}});
    }

    public static async selectOption(id: string, value: string): Promise<void> {
        fireEvent.change((await screen.getByTestId(id)) as HTMLInputElement, {target: {value: value}});
    }
}

async function onSigninCallback(): Promise<void> {
    window.history.replaceState({}, document.title, window.location.pathname);
}
