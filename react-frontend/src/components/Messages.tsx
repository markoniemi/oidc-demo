import * as React from "react";
import { useState } from "react";
import { Alert, Toast, ToastBody, ToastHeader } from "react-bootstrap";
import { FormattedMessage } from "react-intl";
import Message, { MessageType, type MessageVariant } from "../domain/Message";

export interface MessageProps {
    messages?: ReadonlyArray<Message>;
}

export default function Messages(props: MessageProps) {
    const [show, setShow] = useState(true);

    const renderMessage = (message: Message): React.ReactNode => {
        return (
            <Alert variant={mapTypeToStyle(message.type)} key={message.text}>
                <FormattedMessage id={message.text} defaultMessage={message.text} />
            </Alert>
        );
    };

    const mapTypeToStyle = (type: MessageType | undefined): MessageVariant => {
        if (type === MessageType.ERROR) {
            return "danger";
        }
        if (type === MessageType.WARN) {
            return "warning";
        }
        if (type === MessageType.SUCCESS) {
            return "success";
        }
        return "info";
    };

    const onClose = () => {
        setShow(false);
    };

    if (props.messages?.length) {
        return (
            <Toast
                onClose={onClose}
                show={show}
                id="messages"
                style={{
                    position: "absolute",
                    top: 0,
                    right: 0,
                }}
            >
                <ToastHeader />
                <ToastBody>{props.messages.map(renderMessage)}</ToastBody>
            </Toast>
        );
    } else {
        return null;
    }
}
