import React from "react";
import User from "../domain/User";
import UserService from "../api/UserService";
import UserServiceImpl from "../api/UserServiceImpl";
import { FormattedMessage } from "react-intl";
import { Button, Card, Table } from "react-bootstrap";
import UserItem from "./UserRow";
import Message, { MessageType } from "../domain/Message";
import Messages from "./Messages";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import Time from "./Time";
import withRouter, { WithRouter } from "./withRouter";
import { AuthContextProps, withAuth } from "react-oidc-context";

export interface UsersContainerState {
    users: User[];
    messages?: ReadonlyArray<Message>;
}
export interface UsersContainerProps extends  WithRouter{
    auth: AuthContextProps;
}

class UsersContainer extends React.Component<UsersContainerProps, UsersContainerState> {
    private userService: UserService = new UserServiceImpl();

    constructor(props) {
        super(props);
        this.state = { users: [] };
        this.deleteUser = this.deleteUser.bind(this);
        this.addUser = this.addUser.bind(this);
        this.logout = this.logout.bind(this);
    }

    public override async componentDidMount(): Promise<void> {
        try {
            await this.fetchUsers();
        } catch (error) {
            this.setState({ messages: [{ text: error.message, type: MessageType.ERROR }] });
        }
    }

    public addUser(): void {
        this.props.router.navigate("/users/new");
    }

    public async deleteUser(id: number): Promise<void> {
        if (window.confirm("Do you want to delete this item") === true) {
            try {
                await this.userService.delete(id);
            } catch (error) {
                this.setState({ messages: [{ text: error.message, type: MessageType.ERROR }] });
            }
            await this.fetchUsers();
        }
    }

    public override render(): React.ReactNode {
        let userItems;
        if (this.state.users) {
            userItems = this.state.users.map((item, index) => (
                <UserItem key={index} user={item} index={index} deleteUser={this.deleteUser} />
            ));
        }
        return (
            <Card id="UsersContainer">
                <Card.Body>
                    <Card.Title>
                        <FormattedMessage id="users" />
                    </Card.Title>
                    <Messages messages={this.state.messages} />
                    <Table>
                        <thead>
                            <tr>
                                <th>
                                    <FormattedMessage id="username" />
                                </th>
                                <th>
                                    <FormattedMessage id="email" />
                                </th>
                                <th />
                            </tr>
                        </thead>
                        <tbody>{userItems}</tbody>
                    </Table>
                    <Button id="addUser" variant="primary" onClick={this.addUser}>
                        <FontAwesomeIcon icon={Icons.faPlus} />
                    </Button>
                    <Button id="logout" variant="primary" onClick={this.logout}>
                        <FontAwesomeIcon icon={Icons.faSignOutAlt} />
                    </Button>
                </Card.Body>
                <Card.Footer>
                    <Time />
                </Card.Footer>
            </Card>
        );
    }

    private async fetchUsers(): Promise<void> {
        let users: User[];
        try {
            users = await this.userService.fetchUsers();
        } catch (error) {
            this.setState({ messages: [{ text: error.message, type: MessageType.ERROR }] });
        }
        this.setState(() => ({ users: users }));
    }

    private async logout() {
        // await LoginService.logout();
        this.props.auth.removeUser();
        this.props.router.navigate("/");
    }
}

export default withAuth(withRouter(UsersContainer));
