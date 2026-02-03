import * as dotenv from "dotenv";
import fetchMock from "fetch-mock";
import { sleep } from "../time";
import Time from "../../src/components/Time";
import { act, configure, fireEvent, render, screen } from "@testing-library/react";
import AbstractPage from "../pages/AbstractPage";
import { afterEach, assert, beforeEach, describe, test } from "vitest";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const renderTime = async () => {
  const queryClient = new QueryClient();
  await render(
    <QueryClientProvider client={queryClient}>
      <Time />
    </QueryClientProvider>,
  );
};

describe("Time component", () => {
  beforeEach(() => {
    configure({ testIdAttribute: "id" });
    dotenv.config({ path: ".env" });
    fetchMock.mockGlobal();
  });
  afterEach(() => {
    fetchMock.hardReset();
  });
  test("renders text", async () => {
    fetchMock.post("/api/time", "response");
    await act(async () => {
      await renderTime();
    });
    await sleep(100);
    assert.equal((await screen.findByTestId("message")).textContent, "response");
  });
  test("updates text after button press", async () => {
    fetchMock.postOnce("/api/time", "response1");
    fetchMock.postOnce("/api/time", "response2");
    await act(async () => {
      await renderTime();
    });
    await sleep(500);
    assert.equal((await screen.findByTestId("message")).textContent, "response1");
    await act(async () => {
      fireEvent.click(await AbstractPage.findButton("fetchMessage"));
    });
    await sleep(500);
    await act(async () => {
      assert.equal((await screen.findByTestId("message")).textContent, "response2");
    });
  });
});
