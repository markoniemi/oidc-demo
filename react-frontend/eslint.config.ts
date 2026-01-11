import globals from "globals";
import { defineConfig } from "eslint/config";
import reactHooks from "eslint-plugin-react-hooks";
import reactRefresh from "eslint-plugin-react-refresh";
import prettier from "eslint-plugin-prettier";
import importPlugin from "eslint-plugin-import";
import react from "eslint-plugin-react";
import jsxA11y from "eslint-plugin-jsx-a11y";

export default defineConfig([
  { ignores: ["dist"] },
  {
    languageOptions: {
      ecmaVersion: 2020,
      globals: globals.browser,
      parserOptions: {
        ecmaVersion: "latest",
        ecmaFeatures: { jsx: true },
        sourceType: "module",
      },
    },
    plugins: {
      reactHooks: reactHooks as any,
      reactRefresh,
      prettier,
      import: importPlugin,
      react,
      jsxA11y,
    },
    rules: {},
    settings: {
      react: {
        version: "detect",
      },
    },
  },
]);
