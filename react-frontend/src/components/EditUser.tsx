import * as React from "react";
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
import { type NavigateFunction, useNavigate, useParams } from "react-router";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";

export default function EditUser() {
    const userService: UserService = new UserServiceImpl();
    const [messages, setMessages] = React.useState<ReadonlyArray<Message>>();
    const navigate: NavigateFunction = useNavigate();
    const params = useParams();
    const queryClient = useQueryClient();

    const fetchUser = async (): Promise<User> => {
      const id = Number(params.id);
      if (id) {
        return await userService.findById(id);
      }
      return { username: "", password: "", email: "", role: undefined };
    };

    const { data: user, error } = useQuery({
      queryKey: ["user", params.id],
      queryFn: fetchUser,
      retry: false,
      initialData: { username: "", password: "", email: "", role: undefined },
    });

    // useMutation for create/update
    const saveMutation = useMutation({
      mutationFn: async (user: User) => {
        if (user.id == undefined) {
          return await userService.create(user);
        } else {
          return await userService.update(user);
        }
      },
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["users"] });
        navigate("/users");
      },
      onError: (error: unknown) => {
        if (error instanceof Error) {
          setMessages([{ text: error.message, type: MessageType.ERROR }]);
        }
      },
    });

    if (error) {
      return <Messages messages={[{ text: error.message, type: MessageType.ERROR }]} />;
    }

    const schema = Yup.object().shape({
        username: Yup.string().required("username.required"),
        email: Yup.string().required("email.required"),
        password: Yup.string().required("password.required"),
        role: Yup.string().required("role.required"),
    });

    const onSubmit = async (user: User) => {
        saveMutation.mutate(user);
    };

    const onCancel = async () => {
        navigate("/users");
    };

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
                            <Button type="submit" id="saveUser" size="sm" className="pull-right" disabled={saveMutation.isPending}>
                                <FontAwesomeIcon icon={Icons.faCheckSquare}/>
                            </Button>
                            <Button id="cancel" size="sm" className="pull-right" onClick={onCancel}>
                                <FontAwesomeIcon icon={Icons.faCancel}/>
                            </Button>
                        </Col>
                    </Row>
                    {saveMutation.isPending && <div>Saving...</div>}
                </Form.Group>
            </FormikForm>
        );
    };

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
