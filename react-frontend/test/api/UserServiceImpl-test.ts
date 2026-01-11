import * as dotenv from "dotenv";
import "isomorphic-fetch";
import type UserService from "../../src/api/UserService";
import UserServiceImpl from "../../src/api/UserServiceImpl";
import fetchMock from "fetch-mock";
import { user1, users } from "../users";
import User from "../../src/domain/User";
import { afterEach, assert, beforeEach, describe, expect, test } from "vitest";

const userService: UserService = new UserServiceImpl();
describe("UserService", () => {
  beforeEach(() => {
    dotenv.config({ path: ".env" });
    fetchMock.mockGlobal();
  });
  afterEach(() => {
    fetchMock.hardReset();
  });
  test("fetchUsers", async () => {
    fetchMock.getOnce("/api/rest/users/", users);
    const fetchedUsers = await userService.findAll();
    assert.equal(fetchedUsers.length, 2);
  });
  test("findAll fails", async () => {
    fetchMock.getOnce("/api/rest/users/", 401);
    expect(userService.findAll()).rejects.toThrow();
  });
  test("findById", async () => {
    fetchMock.getOnce("/api/rest/users/1", user1);
    const user = await userService.findById(1);
    assert.equal(user.username, "user1");
  });
  test("findById", async () => {
    fetchMock.getOnce("/api/rest/users/1", 401);
    expect(userService.findById(1)).rejects.toThrow();
  });
  test("create", async () => {
    const userMock: User = new User("user3", "user3", "email3", 3);
    fetchMock.postOnce("/api/rest/users/", userMock);
    const user: User = new User("user3", "user3", "email3");
    const savedUser = await userService.create(user);
    assert.equal(savedUser.username, "user3");
    assert.equal(savedUser.id, 3);
  });
  test("create fails", async () => {
    fetchMock.postOnce("/api/rest/users/", 500);
    expect(userService.create(null)).rejects.toThrow();
  });
  test("update", async () => {
    const userMock: User = new User("user2", "user2", "email2", 2);
    fetchMock.putOnce("/api/rest/users/2", userMock);
    const user: User = new User("user2", "user2", "email2", 2);
    const savedUser = await userService.update(user);
    assert.equal(savedUser.username, "user2");
  });
  test("update fails", async () => {
    fetchMock.putOnce("/api/rest/users/1", 500);
    expect(userService.update(user1)).rejects.toThrow();
  });
  test("delete", async () => {
    fetchMock.deleteOnce("/api/rest/users/2", 200);
    userService.delete(2);
  });
  test("delete fails", async () => {
    fetchMock.deleteOnce("/api/rest/users/2", 500);
    expect(userService.delete(2)).rejects.toThrow();
  });
});
