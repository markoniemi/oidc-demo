import Http from "./Http";

export default async function getTime(): Promise<string> {
    const response: Response = await Http.post(getApiUrl(), "world");
    if (response.ok) {
        return response.text();
    } else {
        throw new Error("error.time");
    }
}

function getApiUrl(): string {
    return "/api/rest/time";
}
