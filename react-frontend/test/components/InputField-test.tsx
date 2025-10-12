import * as dotenv from "dotenv";
import * as React from "react";
import User from "../../src/domain/User";
import {user1} from "../users";
import {act, configure, fireEvent, render, screen} from "@testing-library/react";
import i18nConfig from "../../src/messages/messages";
import {IntlProvider} from "react-intl";
import {InputField} from "../../src/components/InputField";
import {Formik} from "formik";
import * as Yup from "yup";
import {ObjectSchema} from "yup";
import {assert, beforeEach, describe, test, vi} from "vitest";

describe("InputField component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
        dotenv.config({path: ".env"});
    });
    test("renders an input field", async () => {
        const onSubmit = vi.fn();
        const schema = Yup.object().shape({
            username: Yup.string().required("username.required"),
        });
        await renderInputField(user1, schema, onSubmit);
        assert.equal(((await screen.findByTestId("username")) as HTMLInputElement).value, "user1");
    });
    test("shows and clears error", async () => {
        const onSubmit = vi.fn();
        const schema = Yup.object().shape({
            username: Yup.string().required("username.required"),
        });
        await renderInputField(user1, schema, onSubmit);
        const input = (await screen.findByTestId("username")) as HTMLInputElement;
        await setValue(input, "");
        assert.isTrue(((await screen.findByTestId("username")) as HTMLInputElement).classList.contains("is-invalid"));
        await setValue(input, "text");
        assert.isFalse(((await screen.findByTestId("username")) as HTMLInputElement).classList.contains("is-invalid"));
    });
});

async function renderInputField(user: User, schema: ObjectSchema<any>, onSubmit: () => void): Promise<void> {
    await act(async () => {
        render(
            <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                <Formik initialValues={user} onSubmit={onSubmit} enableReinitialize={true} validationSchema={schema}>
                    {renderForm}
                </Formik>
            </IntlProvider>,
        );
    });
}

function renderForm(): React.ReactNode {
    return <InputField name="username"/>;
}

async function setValue(input: HTMLInputElement, value?: string) {
    await act(async () => {
        fireEvent.change(input, {target: {value: value}});
    });
}
