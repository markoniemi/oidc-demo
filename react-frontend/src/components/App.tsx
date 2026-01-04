import { AuthProvider } from "react-oidc-context";
import OidcService from "../api/OidcService";
import Content from "./Content";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

export default function App() {
  const queryClient = new QueryClient();

  const onSigninCallback = async (): Promise<void> => {
    window.history.replaceState({}, document.title, window.location.pathname);
  };

  return (
    <AuthProvider {...OidcService.oidcConfig} onSigninCallback={onSigninCallback}>
      <QueryClientProvider client={queryClient}>
        <Content />
      </QueryClientProvider>
    </AuthProvider>
  );
}
