import * as path from "path";
import * as webpack from "webpack";
import CopyWebpackPlugin from "copy-webpack-plugin";

const config: webpack.Configuration = {
    mode: "production",
    entry: "./src/index.tsx",
    output: {
        path: path.resolve(__dirname, "build"),
        filename: "app.js",
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json"],
    },
    module: {
        rules: [
            { test: /\.tsx?$/, exclude: /node_modules/, use: ["ts-loader"] },
            { test: /\.css$/, use: ["style-loader", "css-loader"] },
            { test: /\.json$/, use: ["json-loader"] },
            {
                test: /\.(eot|woff|woff2|ttf|svg|png|jpe?g|gif)(\?\S*)?$/,
                use: ["file-loader"],
            },
        ],
    },
    plugins: [new CopyWebpackPlugin({ patterns: [{ from: "public", to: "." }] })],
};

export default config;
