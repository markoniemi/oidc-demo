import Role from "./Role";

export default class User {
    public id?: number;
    public username: string;
    public password: string;
    public email: string;
    public role?: Role;

    constructor(username?: string, password?: string, email?: string, id?: number, role?: Role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
