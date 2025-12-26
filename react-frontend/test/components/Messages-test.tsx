import Messages from "../../src/components/Messages";
import {messages} from "../messages";
import {configure, render, screen} from "@testing-library/react";
import i18nConfig from "../../src/messages/messages";
import {IntlProvider} from "react-intl";
import {assert, beforeEach, describe, test} from "vitest";

describe("Messages component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
    });
    test("creates no error with empty message list", async () => {
        render(
            <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                <Messages messages={[]}/>
            </IntlProvider>,
        );
        assert.isNull(await screen.queryByTestId("messages"));
    });
    test("renders messages", async () => {
        render(
            <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
                <Messages messages={messages}/>
            </IntlProvider>,
        );
        assert.isNotNull(await screen.findByTestId("messages"));
        assert.isNotNull(await screen.findAllByText("success"));
        assert.isNotNull(await screen.findAllByText("info"));
        assert.isNotNull(await screen.findAllByText("warn"));
        assert.isNotNull(await screen.findAllByText("error"));
    });
});
