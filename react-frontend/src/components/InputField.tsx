import React from "react";
import Empty from "../domain/Empty";
import { Col, Form, FormControlProps, Row } from "react-bootstrap";
import { FormattedMessage } from "react-intl";
import { ErrorMessage, FormikProps, FormikValues } from "formik";

interface InputProps extends FormControlProps {
    name: string;
    formik: FormikProps<FormikValues>;
}

export default class InputField extends React.Component<InputProps, Empty> {
    constructor(props: InputProps) {
        super(props);
        this.onKeyPress = this.onKeyPress.bind(this);
    }

    public override render(): React.ReactNode {
        const { name, formik, children } = this.props;
        return (
            <Row className="mt-2">
                <Col sm={1}>
                    <Form.Label>
                        <FormattedMessage id={name}/>:
                    </Form.Label>
                </Col>
                <Col sm={4}>
                    <Form.Control
                        id={name}
                        name={name}
                        size="sm"
                        onChange={formik.handleChange}
                        value={formik.values[name]}
                        onKeyPress={this.onKeyPress}
                        isInvalid={formik.errors[name] != null}
                        {...this.props}
                    >
                        {children}
                    </Form.Control>
                </Col>
                <Col>
                    <ErrorMessage name={name}>
                        {(message) => <FormattedMessage id={message} defaultMessage={message} />}
                    </ErrorMessage>
                </Col>
            </Row>
        );
    }

    private async onKeyPress(event: React.KeyboardEvent<HTMLInputElement>): Promise<void> {
        if ("Enter" === event.key) {
            await this.props.formik.submitForm();
        }
    }
}
