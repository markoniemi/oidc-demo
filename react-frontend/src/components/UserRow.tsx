import * as React from "react";
import User from "../domain/User";
import {NavLink} from "react-router";
import {Button} from "react-bootstrap";
import {FormattedMessage} from "react-intl";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import type {Empty} from "../domain/Empty";
import withRouter, {type WithRouter} from "./withRouter";

export interface UserProps extends WithRouter {
    user: User;

    deleteUser(id: number | undefined): void;
}

class UserRow extends React.Component<UserProps, Empty> {
    constructor(props: UserProps) {
        super(props);
        this.delete = this.delete.bind(this);
        this.editUser = this.editUser.bind(this);
    }

    public delete(): void {
        const {id} = this.props.user;
        this.props.deleteUser(id);
    }

    public editUser(): void {
        const {id} = this.props.user;
        this.props.router.navigate("/users/" + id);
    }

    public override render(): React.ReactNode {
        const user: User = this.props.user;
        return (
            <tr id={user.username} key={this.props.user.id}>
                <td id="username">
                    <NavLink to={"/users/" + user.id}>{user.username}</NavLink>
                </td>
                <td id="email">{user.email}</td>
                <td id="role">
                    <FormattedMessage id={"role." + user.role}/>
                </td>
                <td>
                    <Button id={`edit.${user.username}`} size="sm" onClick={this.editUser}>
                        <FontAwesomeIcon icon={Icons.faEdit}/>
                    </Button>
                    <Button id={`delete.${user.username}`} size="sm" onClick={this.delete}>
                        <FontAwesomeIcon icon={Icons.faTrashAlt}/>
                    </Button>
                </td>
            </tr>
        );
    }
}

export default withRouter(UserRow);
