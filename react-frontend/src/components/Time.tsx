import * as React from "react";
import { useEffect } from "react";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import { useIsMounted } from "usehooks-ts";
import getTime from "../api/TimeService.ts";

export default function Time() {
    const [message, setMessage] = React.useState<string>("");
    const isMounted = useIsMounted();
    const fetchMessage = async () => {
        setMessage(await getTime());
    };
    useEffect(() => {
        fetchMessage();
    }, [isMounted]);
    return (
        <>
            <Button id="fetchMessage" size="sm" onClick={fetchMessage}>
                <FontAwesomeIcon icon={Icons.faClock} />
            </Button>
            <span id="message">{message}</span>
        </>
    );
}
