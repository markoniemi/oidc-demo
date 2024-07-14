import User from "../src/domain/User";
import Role from "../src/domain/Role";

export const user1: User = { username: "user1", email: "email1", password: "password1", id: 1, role: Role.ROLE_USER };
export const user2: User = { username: "user2", email: "email2", password: "password2", id: 2, role: Role.ROLE_USER };
export const users: User[] = [user1, user2];
