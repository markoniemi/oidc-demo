import { TimeService } from "./TimeService";
import Http from "./Http";

export default class TimeServiceImpl implements TimeService {
    public async getTime(): Promise<string> {
        const response: Response = await Http.post(this.getApiUrl(), "world");
        if (response.ok) {
            return response.text();
        } else {
            throw new Error("error.time");
        }
    }

    public getApiUrl(): string {
        return "/api/rest/time";
    }
}
