import type { Config } from "jest";

const config: Config = {
    preset: "ts-jest",
    testEnvironment: "jsdom",
    setupFiles: ["./test/jest.setup.ts", "jest-localstorage-mock"],
    moduleFileExtensions: ["ts", "tsx", "js"],
    transform: {
        "^.+\\.tsx?$": "ts-jest",
    },
    testRegex: "(/test/.*-(test|it))\\.tsx?",
    testPathIgnorePatterns: ["__snapshots__"],
    coverageDirectory: "reports/coverage",
    coveragePathIgnorePatterns: ["src/index.tsx"],
    collectCoverageFrom: ["src/**", "server/**"],
    testEnvironmentOptions: {
        url: "http://localhost:8080",
    },
    reporters: [
        "default",
        [
            "jest-junit",
            {
                outputDirectory: "reports/test",
                outputName: "TESTS.xml",
            },
        ],
    ],
};

export default config;
