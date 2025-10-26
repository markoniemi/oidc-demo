import * as React from "react";
import { Button, Card, Col, Form, Row } from "react-bootstrap";
import { FormattedMessage } from "react-intl";
import Messages from "./Messages";
import LoginService from "../api/LoginService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import Jwt from "../api/Jwt";
import Message, { MessageType } from "../domain/Message";
import { Form as FormikForm, Formik, type FormikProps } from "formik";
import * as Yup from "yup";
import {InputField} from "./InputField";
import { type AuthContextProps, withAuth } from "react-oidc-context";
import withRouter, {type WithRouter} from "./withRouter";

export interface ILoginForm {
    username: string;
    password: string;
}

export interface ILoginState extends ILoginForm {
    messages?: ReadonlyArray<Message>;
}

export interface LoginProps extends WithRouter {
    auth: AuthContextProps;
}

class LoginForm extends React.Component<LoginProps, ILoginState> {
    private schema = Yup.object().shape({
        username: Yup.string().required("username.required"),
        password: Yup.string().required("password.required"),
    });

    constructor(props: LoginProps) {
        super(props);
        this.onSubmit = this.onSubmit.bind(this);
        this.login = this.login.bind(this);
        this.renderForm = this.renderForm.bind(this);
        this.state = { username: "", password: "" };
    }

    public render(): React.ReactNode {
        return (
            <Card id="LoginForm">
                <Row>
                    <Col md={{ span: 10, offset: 4 }}>
                        <Card.Body>
                            <Card.Title>
                                <FormattedMessage id="login" />
                            </Card.Title>
                            <Messages messages={this.state.messages} />
                            <Formik
                                initialValues={this.state}
                                onSubmit={this.onSubmit}
                                enableReinitialize={true}
                                validationSchema={this.schema}
                            >
                                {this.renderForm}
                            </Formik>
                        </Card.Body>
                    </Col>
                </Row>
                <Row>
                    <Col md={{ span: 10, offset: 4 }}>
                        <Card.Body>
                            <Button
                                id="login.google"
                                size="sm"
                                className="pull-right"
                                onClick={() => void this.props.auth.signinRedirect()}
                            >
                                <FontAwesomeIcon icon={Icons.faRightToBracket} />
                                <FormattedMessage id="login.google" />
                            </Button>
                        </Card.Body>
                    </Col>
                </Row>
            </Card>
        );
    }

    private renderForm(form: FormikProps<ILoginForm>): React.ReactNode {
        return (
            <FormikForm>
                <Form.Group>
                    <InputField name="username" formik={form} />
                    <InputField name="password" type="password" formik={form} />
                    <Col sm={5}>
                        <Button id="login" size="sm" className="pull-right" type="submit">
                            <FontAwesomeIcon icon={Icons.faCheckSquare} />
                        </Button>
                    </Col>
                </Form.Group>
            </FormikForm>
        );
    }

    public async onSubmit(values: ILoginForm) {
        this.setState({ ...values });
        await this.login({ ...values });
    }

    private async login(loginForm: ILoginForm): Promise<void> {
        try {
            const token = await LoginService.login(loginForm);
            Jwt.setToken(token);
            window.location.href = "/users";
            this.props.router.navigate("/users");
        } catch (error) {
            // @ts-ignore
            this.setState({ messages: [{ text: error.message, type: MessageType.ERROR }] });
        }
    }
}

export default withAuth(withRouter(LoginForm));
