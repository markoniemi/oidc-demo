import * as React from "react";
import {useEffect} from "react";
import User from "../domain/User";
import type UserService from "../api/UserService";
import UserServiceImpl from "../api/UserServiceImpl";
import {FormattedMessage} from "react-intl";
import {Button, Card, Table} from "react-bootstrap";
import UserItem from "./UserRow";
import Message, {MessageType} from "../domain/Message";
import Messages from "./Messages";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import LoginService from "../api/LoginService";
import Time from "./Time";
import {type AuthContextProps, useAuth} from "react-oidc-context";
import {useIsMounted} from "usehooks-ts";
import {type NavigateFunction, useNavigate} from "react-router";

export interface UsersContainerState {
    users: User[];
    messages?: ReadonlyArray<Message>;
}

export default function UsersContainer() {
    const userService: UserService = new UserServiceImpl();
    const [messages, setMessages] = React.useState<ReadonlyArray<Message>>();
    const [users, setUsers] = React.useState<User[]>();
    const auth: AuthContextProps = useAuth();
    const navigate: NavigateFunction = useNavigate();
    const isMounted = useIsMounted();
    const fetchUsers = async () => {
        try {
            setUsers(await userService.fetchUsers());
        } catch (error: unknown) {
            if (error instanceof Error) {
                setMessages([{text: error.message, type: MessageType.ERROR}]);
            }
        }
    }

    useEffect(() => {
        fetchUsers();
    }, [isMounted]);
    const addUser = (): void => {
        navigate("/users/new");
    }

    const deleteUser = async (id: number): Promise<void> => {
        if (window.confirm("Do you want to delete this item")) {
            try {
                await userService.delete(id);
            } catch (error: unknown) {
                if (error instanceof Error) {
                    setMessages([{text: error.message, type: MessageType.ERROR}]);
                }
            }
            await userService.fetchUsers();
        }
    }

    const logout = async () => {
        await LoginService.logout();
        auth.removeUser();
        navigate("/");
    }

    let userItems;
    if (users) {
        userItems = users.map((item, index) => (
            <UserItem key={index} user={item} deleteUser={deleteUser}/>
        ));
    }
    return (
        <Card id="UsersContainer">
            <Card.Body>
                <Card.Title>
                    <FormattedMessage id="users"/>
                </Card.Title>
                <Messages messages={messages}/>
                <Table>
                    <thead>
                    <tr>
                        <th>
                            <FormattedMessage id="username"/>
                        </th>
                        <th>
                            <FormattedMessage id="email"/>
                        </th>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>{userItems}</tbody>
                </Table>
                <Button id="addUser" variant="primary" onClick={addUser}>
                    <FontAwesomeIcon icon={Icons.faPlus}/>
                </Button>
                <Button id="logout" variant="primary" onClick={logout}>
                    <FontAwesomeIcon icon={Icons.faSignOutAlt}/>
                </Button>
            </Card.Body>
            <Card.Footer>
                <Time/>
            </Card.Footer>
        </Card>
    );
}

