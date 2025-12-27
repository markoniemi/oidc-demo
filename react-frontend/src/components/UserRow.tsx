import User from "../domain/User";
import { type NavigateFunction, NavLink, useNavigate } from "react-router";
import { Button } from "react-bootstrap";
import { FormattedMessage } from "react-intl";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";

export interface UserProps {
    user: User;
    deleteUser?(id: number | undefined): void;
}

export default function UserRow(props: UserProps) {
    const navigate: NavigateFunction = useNavigate();
    const user: User = props.user;

    const deleteUser = (): void => {
        const { id } = user;
        if (props.deleteUser) {
            props.deleteUser(id);
        }
    };

    const editUser = (): void => {
        const { id } = user;
        navigate("/users/" + id);
    };

    return (
        <tr id={user.username} key={user.id}>
            <td id="username">
                <NavLink to={"/users/" + user.id}>{user.username}</NavLink>
            </td>
            <td id="email">{user.email}</td>
            <td id="role">
                <FormattedMessage id={"role." + user.role} />
            </td>
            <td>
                <Button id={`edit.${user.username}`} size="sm" onClick={editUser}>
                    <FontAwesomeIcon icon={Icons.faEdit} />
                </Button>
                <Button id={`delete.${user.username}`} size="sm" onClick={deleteUser}>
                    <FontAwesomeIcon icon={Icons.faTrashAlt} />
                </Button>
            </td>
        </tr>
    );
}
