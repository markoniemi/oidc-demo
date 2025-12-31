module.exports = {
    parser: "@typescript-eslint/parser",
    extends: [
        "plugin:react/recommended",
        "plugin:prettier/recommended",
        "prettier",
        "plugin:@typescript-eslint/recommended",
    ],
    parserOptions: {
        ecmaVersion: 2018,
        sourceType: "module",
        ecmaFeatures: {
            jsx: true,
        },
    },
    rules: {
        "prettier/prettier": "warn",
        "@typescript-eslint/ban-types": "warn",
        "@typescript-eslint/ban-ts-comment": "warn",
    },
    settings: {
        react: {
            version: "detect",
        },
    },
    env: {
        browser: true,
    },
};
