import { Configuration, SecretApi, TodoListApi, UserApi } from "@z/api-client";

const configuration = new Configuration({
  basePath: "/api/v1/",
});

export const userApi = new UserApi(configuration);
export const secretApi = new SecretApi(configuration);
export const todoListApi = new TodoListApi(configuration);
