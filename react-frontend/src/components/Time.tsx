import TimeServiceImpl from "../api/TimeServiceImpl";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as Icons from "@fortawesome/free-solid-svg-icons";
import { useQuery } from "@tanstack/react-query";

export default function Time() {
  const helloService = new TimeServiceImpl();
  const { data: message, refetch } = useQuery({
    queryKey: ["time"],
    queryFn: () => helloService.getTime(),
    retry: false,
  });

  return (
    <>
      <Button id="fetchMessage" size="sm" onClick={() => refetch()}>
        <FontAwesomeIcon icon={Icons.faClock} />
      </Button>
      <span id="message">{message}</span>
    </>
  );
}
