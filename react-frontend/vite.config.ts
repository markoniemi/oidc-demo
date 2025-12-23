import {defineConfig, loadEnv} from "vite";
import react from "@vitejs/plugin-react";
import devtoolsJson from 'vite-plugin-devtools-json';

export default defineConfig(({ mode }) => {
    const env = loadEnv(mode, process.cwd(), "");
    return {
        plugins: [react(),
            devtoolsJson()],
        server: {
            port: 8081,
            proxy: {
                "/api": `http://${env.BACKEND_HOST}:${env.BACKEND_PORT}`,
            },
        },
        build: {
            outDir: "build",
            minify: false,
            sourcemap: true,
        },
        define: {
            'process.env': env,
        },
    };
});
