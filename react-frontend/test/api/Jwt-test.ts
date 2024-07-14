import { assert } from "chai";
import Jwt from "../../src/api/Jwt";

describe("Jwt", () => {
    test("isAuthenticated returns false if sessionStorage does not have token", () => {
        assert.isFalse(Jwt.isAuthenticated());
    });
    test("isAuthenticated returns true if sessionStorage has token", () => {
        Jwt.setToken("token");
        sessionStorage.setItem(Jwt.JWT_TOKEN_KEY, "token");
        assert.isTrue(Jwt.isAuthenticated());
    });
    test("isAuthenticated returns false if sessionStorage is cleared", () => {
        sessionStorage.setItem(Jwt.JWT_TOKEN_KEY, "token");
        Jwt.clearToken();
        assert.isFalse(Jwt.isAuthenticated());
    });
    test("getToken returns null if sessionStorage does not have token", () => {
        assert.isNull(Jwt.getToken());
    });
    test("getToken returns token if sessionStorage has token", () => {
        Jwt.setToken("token");
        sessionStorage.setItem(Jwt.JWT_TOKEN_KEY, "token");
        assert.equal(Jwt.getToken(), "token");
    });
    test("getToken returns null if sessionStorage is cleared", () => {
        sessionStorage.setItem(Jwt.JWT_TOKEN_KEY, "token");
        Jwt.clearToken();
        assert.isNull(Jwt.getToken());
    });
});
