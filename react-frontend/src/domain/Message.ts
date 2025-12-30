export enum MessageType {
    INFO = "INFO",
    WARN = "WARN",
    ERROR = "ERROR",
    SUCCESS = "SUCCESS",
}

export type MessageVariant = "primary" | "secondary" | "success" | "danger" | "warning" | "info" | "dark" | "light";

export default class Message {
    public id?: string;
    public text: string;
    public type?: MessageType;

    constructor() {
        this.id = "";
        this.text = "";
        this.type = undefined;
    }
}
