import * as dotenv from "dotenv";
import fetchMock from "fetch-mock";
import {sleep} from "../time";
import Time from "../../src/components/Time";
import {act, configure, fireEvent, render, screen} from "@testing-library/react";
import AbstractPage from "../pages/AbstractPage";
import {afterEach, assert, beforeEach, describe, test} from "vitest";

describe("Time component", () => {
    beforeEach(() => {
        configure({testIdAttribute: "id"});
        dotenv.config({path: ".env"});
        fetchMock.mockGlobal();
    });
    afterEach(() => {
        fetchMock.hardReset();
    });
    test("renders text", async () => {
        fetchMock.post("/api/rest/time", "response");
        await act(async () => {
            await render(<Time/>);
        });
        await sleep(100);
        assert.equal((await screen.findByTestId("message")).textContent, "response");
    },20000);
    test("updates text after button press", async () => {
        fetchMock.postOnce("/api/rest/time", "response1");
        fetchMock.postOnce("/api/rest/time", "response2");
        await act(async () => {
            await render(<Time/>);
        });
        await sleep(100);
        assert.equal((await screen.findByTestId("message")).textContent, "response1");
        await act(async () => {
            await fireEvent.click(await AbstractPage.findButton("fetchMessage"));
            await sleep(100);
        });
        assert.equal((await screen.findByTestId("message")).textContent, "response2");
    }, 20000);
});
