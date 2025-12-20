import {defineConfig} from 'vitest/config'
import react from "@vitejs/plugin-react";
import {loadEnv} from "vite";

export default defineConfig(({ mode }) => {
    const env = loadEnv(mode, process.cwd(), "");
    return {
        plugins: [react()],
        test: {
            globals: true,
            environment: "jsdom",
            include: ["test/**/*-test.ts?(x)"],
            coverage: {
                reportsDirectory: "reports/coverage",
                include: ["test/**", "src/**"]
            },
        },
        define: {
            'process.env': env,
        },
    };
});
