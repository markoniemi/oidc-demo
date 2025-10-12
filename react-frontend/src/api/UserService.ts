import User from "../domain/User";

export default interface UserService {
    findById(id: number): Promise<User>;

    fetchUsers(): Promise<User[]>;

    create(newUser: User | null): Promise<User>;

    update(editedUser: User): Promise<User>;

    delete(id: number): Promise<void>;
}
