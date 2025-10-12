import {useField} from 'formik';
import type {PropsWithChildren} from 'react';
import {Col, Form, Row} from 'react-bootstrap';
import {FormattedMessage} from 'react-intl';
import type {FieldAttributes} from "formik/dist/Field";

export const InputField: React.FC<PropsWithChildren & FieldAttributes<{}>> = ({...props}) => {
    const [field, meta] = useField(props);

    return (
        <Row className="mt-2">
            <Col sm={1}>
                <Form.Label>
                    <FormattedMessage id={field.name + "_label"}/>:
                </Form.Label>
            </Col>
            <Col sm={4}>
                <Form.Control
                    id={field.name}
                    size="sm"
                    isInvalid={meta.error != null}
                    {...field}
                    {...props}
                >
                    {props.children}
                </Form.Control>
            </Col>
            <Col>
                {meta.touched && meta.error ? (
                    <FormattedMessage id={meta.error} defaultMessage={meta.error}/>
                ) : null}
            </Col>
        </Row>
    );
};
