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
import { InputField } from "./InputField";
import { useAuth, type AuthContextProps } from "react-oidc-context";
import { useNavigate, type NavigateFunction } from "react-router";

export interface ILoginForm {
    username: string;
    password: string;
}

export interface ILoginState extends ILoginForm {
    messages?: ReadonlyArray<Message>;
}

export default function LoginForm() {
    const [messages, setMessages] = React.useState<ReadonlyArray<Message>>();
    const [form, setForm] = React.useState<ILoginForm>({ username: "", password: "" });
    const schema = Yup.object().shape({
        username: Yup.string().required("username.required"),
        password: Yup.string().required("password.required"),
    });
    const auth: AuthContextProps = useAuth();
    const navigate: NavigateFunction = useNavigate();

    const renderForm = (form: FormikProps<ILoginForm>): React.ReactNode => {
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
    };

    const onSubmit = async (values: ILoginForm): Promise<void> => {
        setForm({ ...values });
        await login({ ...values });
    };

    const login = async (loginForm: ILoginForm): Promise<void> => {
        try {
            const token = await LoginService.login(loginForm);
            Jwt.setToken(token);
            window.location.assign("/users");
            navigate("/users");
        } catch (error: any) {
            setMessages([{ text: error.message, type: MessageType.ERROR }]);
        }
    };

    return (
        <Card id="LoginForm">
            <Row>
                <Col md={{ span: 10, offset: 4 }}>
                    <Card.Body>
                        <Card.Title>
                            <FormattedMessage id="login" />
                        </Card.Title>
                        <Messages messages={messages} />
                        <Formik
                            initialValues={form}
                            onSubmit={onSubmit}
                            enableReinitialize={true}
                            validationSchema={schema}
                        >
                            {renderForm}
                        </Formik>
                    </Card.Body>
                </Col>
            </Row>
            <Row>
                <Col md={{ span: 10, offset: 4 }}>
                    <Card.Body>
                        <Button
                            id="login.oauth"
                            size="sm"
                            className="pull-right"
                            onClick={() => void auth.signinRedirect()}
                        >
                            <FontAwesomeIcon icon={Icons.faRightToBracket} />
                            <FormattedMessage id="login.oauth" />
                        </Button>
                    </Card.Body>
                </Col>
            </Row>
        </Card>
    );
}
