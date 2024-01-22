export type SupportedHttpMethod = "GET" | "POST" | "PUT" | "DELETE" | "PATCH";

export const createApiRequest = async (
  endpoint: string,
  method: SupportedHttpMethod,
  body: unknown,
) => {
  const realPath = `/api/v1${endpoint}`;
  const response = await fetch(realPath, {
    method,
    body: method === "GET" ? undefined : JSON.stringify(body),
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
  });
  if (response.ok) {
    return response;
  }
  throw new Error(response.statusText);
};
