import {useField} from 'formik';
import type { FC, PropsWithChildren, ComponentProps } from "react";
import {Col, Form, Row} from 'react-bootstrap';
import {FormattedMessage} from 'react-intl';

// Define props specifically for InputField, extending react-bootstrap's FormControl props
// and adding the 'name' prop required by useField.
type InputFieldProps = ComponentProps<typeof Form.Control> & {
    name: string;
};

export const InputField: FC<PropsWithChildren<InputFieldProps>> = ({ children, ...props }) => {
    const [field, meta] = useField(props.name);

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
                    isInvalid={!!meta.touched && !!meta.error}
                    {...field}
                    {...props}
                >
                    {children}
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
