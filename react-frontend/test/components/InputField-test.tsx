import { assert } from "chai";
import * as dotenv from "dotenv";
import * as React from "react";
import User from "../../src/domain/User";
import { user1 } from "../users";
import { act, configure, fireEvent, render, screen } from "@testing-library/react";
import i18nConfig from "../../src/messages/messages";
import { IntlProvider } from "react-intl";
import InputField from "../../src/components/InputField";
import { Formik, FormikProps } from "formik";
import * as Yup from "yup";
import { ObjectSchema } from "yup";

describe("InputField component", () => {
    beforeEach(() => {
        configure({ testIdAttribute: "id" });
        dotenv.config({ path: "config/development.env" });
    });
    test("renders an input field", async () => {
        const onSubmit = jest.fn();
        const schema = Yup.object().shape({
            username: Yup.string().required("username.required"),
        });
        await renderInputField(user1, schema, onSubmit);
        assert.equal(((await screen.findByTestId("username")) as HTMLInputElement).value, "user1");
    });
    test("shows and clears error", async () => {
        const onSubmit = jest.fn();
        const schema = Yup.object().shape({
            username: Yup.string().required("username.required"),
        });
        await renderInputField(user1, schema, onSubmit);
        const input = (await screen.findByTestId("username")) as HTMLInputElement;
        await setValue(input, null);
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

function renderForm(form: FormikProps<User>): React.ReactNode {
    return <InputField name="username" formik={form} />;
}

async function setValue(input: HTMLInputElement, value: string) {
    await act(async () => {
        fireEvent.change(input, { target: { value: value } });
    });
}