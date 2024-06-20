import * as dotenv from "dotenv";
import Dotenv from "dotenv-webpack";
import * as path from "path";
import webpack from "webpack-dev-server";
import CopyWebpackPlugin from "copy-webpack-plugin";

dotenv.config({ path: "config/development.env" });

const webpackConfig: webpack.Configuration = {
    mode: "development",
    devtool: "source-map",
    devServer: {
        hot: true,
        proxy: {
            "/api/*": `http://${process.env.BACKEND_HOST}:${process.env.BACKEND_PORT}`,
        },
        historyApiFallback: true,
        port: process.env.PORT,
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".jsx", ".json"],
    },
    output: {
        path: path.resolve(__dirname, "./dist"),
        // filename: "[name].js",
        filename: "app.js",
    },
    module: {
        rules: [
            { test: /\.tsx?$/, use: ["ts-loader"] },
            { test: /\.css$/, use: ["style-loader", "css-loader"] },
            { test: /\.json$/, use: ["json-loader"] },
            {
                test: /\.(eot|woff|woff2|ttf|svg|png|jpe?g|gif)(\?\S*)?$/,
                use: ["file-loader"],
            },
        ],
    },
    plugins: [
        // Dotenv reads config/development.env file to process.env
        new Dotenv({
            path: "config/development.env",
            systemvars: true,
        }),
        new CopyWebpackPlugin({ patterns: [{ from: "public", to: "dist" }] }),
    ],
};
export default webpackConfig;
