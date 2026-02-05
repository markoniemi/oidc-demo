import User from "../domain/User";
import type UserService from "../api/UserService";
import UserServiceImpl from "../api/UserServiceImpl";
import { FormattedMessage } from "react-intl";
import { Button, Card, Table } from "react-bootstrap";
import UserItem from "./UserRow";
import Message, { MessageType } from "../domain/Message";
import Messages from "./Messages";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import LoginServiceImpl from "../api/LoginServiceImpl.ts";
import Time from "./Time";
import { type AuthContextProps, useAuth } from "react-oidc-context";
import { type NavigateFunction, useNavigate } from "react-router";
import type LoginService from "../api/LoginService.ts";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { type JSX } from "react";

export default function UsersContainer(): JSX.Element {
  const loginService: LoginService = new LoginServiceImpl();
  const userService: UserService = new UserServiceImpl();
  const auth: AuthContextProps = useAuth();
  const navigate: NavigateFunction = useNavigate();
  const queryClient = useQueryClient();
  const { data: users, error, isLoading } = useQuery({ queryKey: ["users"], queryFn: () => userService.findAll(), retry: false });
  let messages: ReadonlyArray<Message> = [];

  const addUser = (): void => {
    navigate("/users/new");
  };

  const deleteMutation = useMutation({
    mutationFn: (id: number) => userService.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["users"] });
    },
    onError: (error: unknown) => {
      if (error instanceof Error) {
        messages = [{ text: error.message, type: MessageType.ERROR }];
      }
    },
  });

  const deleteUser = async (id: number): Promise<void> => {
    if (window.confirm("Do you want to delete this item")) {
      deleteMutation.mutate(id);
    }
  };

  const logout = async () => {
    await loginService.logout();
    await auth.removeUser();
    navigate("/");
  };

  if (error) {
    messages = [{ text: error.message, type: MessageType.ERROR }];
  }

  let userItems: JSX.Element[] = [];
  if (users) {
    userItems = users.map((item: User, index: number) => <UserItem key={index} user={item} deleteUser={deleteUser} />);
  }
  return (
    <Card id="UsersContainer">
      <Card.Body>
        <Card.Title>
          <FormattedMessage id="users" />
        </Card.Title>
        <Messages messages={messages} />
        {isLoading ? (
          <div>Loading...</div>
        ) : (
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
        )}
        <Button id="addUser" variant="primary" onClick={addUser}>
          <FontAwesomeIcon icon={Icons.faPlus} />
        </Button>
        <Button id="logout" variant="primary" onClick={logout}>
          <FontAwesomeIcon icon={Icons.faSignOutAlt} />
        </Button>
      </Card.Body>
      <Card.Footer>
        <Time />
      </Card.Footer>
    </Card>
  );
}
