import User from "../domain/User";
import UserService from "./UserService";
import Http from "./Http";

export default class UserServiceImpl implements UserService {
    private readonly url = `/api/rest/users/`;

    public async fetchUsers(): Promise<User[]> {
        const response: Response = await Http.get(this.url);
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("error.load.users");
        }
    }

    public async findById(id: number): Promise<User> {
        const response: Response = await Http.get(this.url + id);
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("error.load.user");
        }
    }

    public async create(user: User): Promise<User> {
        const response: Response = await Http.post(this.url, JSON.stringify(user));
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("error.save.user");
        }
    }

    public async delete(id: number): Promise<void> {
        const response: Response = await Http.delete(this.url + id);
        if (!response.ok) {
            throw new Error("error.delete.user");
        }
    }

    public async update(user: User): Promise<User> {
        const response: Response = await Http.update(this.url + user.id, JSON.stringify(user));
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("error.save.user");
        }
    }
}
