import * as React from "react";
import {useEffect} from "react";
import User from "../domain/User";
import type UserService from "../api/UserService";
import UserServiceImpl from "../api/UserServiceImpl";
import {FormattedMessage} from "react-intl";
import Messages from "./Messages";
import Message, {MessageType} from "../domain/Message";
import {Button, Card, Col, Form, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import {Form as FormikForm, Formik, type FormikProps} from "formik";
import * as Yup from "yup";
import {InputField} from "./InputField.tsx";
import {useIsMounted} from "usehooks-ts";
import {type NavigateFunction, useNavigate, useParams} from "react-router";

export default function EditUser() {
    const userService: UserService = new UserServiceImpl();
    const [messages, setMessages] = React.useState<ReadonlyArray<Message>>();
    const [user, setUser] = React.useState<User>({username:"",password:"", email:"", role: undefined});
    const navigate: NavigateFunction = useNavigate();
    const params = useParams();
    const isMounted = useIsMounted();

    useEffect(() => {
        fetchUser();
    }, [isMounted]);


    const schema = Yup.object().shape({
        username: Yup.string().required("username.required"),
        email: Yup.string().required("email.required"),
        password: Yup.string().required("password.required"),
        role: Yup.string().required("role.required"),
    });

    const fetchUser = async (): Promise<void> => {
        const id = Number(params.id);
        if (id) {
            try {
                setUser(await userService.findById(id));
            } catch (error: unknown) {
                if (error instanceof Error) {
                    setMessages([{text: error.message, type: MessageType.ERROR}]);
                }
            }
        }
    }

    const onSubmit = async (user: User) => {
        setUser(user);
        await submitUser(user);
    }

    const onCancel = async () => {
        navigate("/users");
    }

    const submitUser = async (user: User): Promise<void> => {
        try {
            if (user.id == undefined) {
                await userService.create(user);
            } else {
                await userService.update(user);
            }
            navigate("/users");
        } catch (error: unknown) {
            if (error instanceof Error) {
                setMessages([{text: error.message, type: MessageType.ERROR}]);
            }
        }
    }

    const renderForm = (form: FormikProps<User>): React.ReactNode => {
        return (
            <FormikForm>
                <Messages messages={messages}/>
                <Form.Group>
                    <InputField
                        name="id"
                        disabled={true}
                        value={form.values.id ? form.values.id.toString() : ""}
                    />
                    <InputField name="username"/>
                    <InputField name="email"/>
                    <InputField name="password" type="password"/>
                    <InputField name="role" type="select" as="select">
                        <option value={""}/>
                        <FormattedMessage id="role.ROLE_ADMIN">
                            {(message) => <option value="ROLE_ADMIN">{message}</option>}
                        </FormattedMessage>
                        <FormattedMessage id="role.ROLE_USER">
                            {(message) => <option value="ROLE_USER">{message}</option>}
                        </FormattedMessage>
                    </InputField>
                    <Row>
                        <Col sm={5}>
                            <Button type="submit" id="saveUser" size="sm" className="pull-right">
                                <FontAwesomeIcon icon={Icons.faCheckSquare}/>
                            </Button>
                            <Button id="cancel" size="sm" className="pull-right" onClick={onCancel}>
                                <FontAwesomeIcon icon={Icons.faCancel}/>
                            </Button>
                        </Col>
                    </Row>
                </Form.Group>
            </FormikForm>
        );
    }

    return (
        <Card id="EditUser">
            <Card.Body>
                <Card.Title>
                    <FormattedMessage id="user"/>
                </Card.Title>
                <Formik
                    initialValues={user}
                    onSubmit={onSubmit}
                    enableReinitialize={true}
                    validationSchema={schema}
                >
                    {renderForm}
                </Formik>
            </Card.Body>
        </Card>
    );
}
