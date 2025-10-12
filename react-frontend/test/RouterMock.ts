export function setLocation(location?: string): void {
    window.history.replaceState({}, "", location);
}
