import {
  Configuration,
  FetchParams,
  Middleware,
  RequestContext,
  SecretApi,
  TodoListApi,
  UserApi,
} from "@z/api-client";
import { getServerToken } from "~/app/jwt";

export const apiEndpointRoot =
  process.env.NEXT_PUBLIC_API_ENDPOINT ?? "http://localhost:8080";

const configuration = new Configuration({
  basePath: apiEndpointRoot,
});

const middleware: Middleware["pre"] = async (context: RequestContext) => {
  const token = await getServerToken();
  return {
    ...context,
    init: {
      ...context.init,
      headers: {
        ...context.init.headers,
        Authorization: `Bearer ${token?.accessToken}`,
      },
    },
  };
};

export const serverSecretApi = new SecretApi(configuration).withPreMiddleware(
  middleware,
);
export const serverTodoListApi = new TodoListApi(
  configuration,
).withPreMiddleware(middleware);
export const serverUserApi = new UserApi(configuration).withPreMiddleware(
  middleware,
);
