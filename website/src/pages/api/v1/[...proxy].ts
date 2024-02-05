import { NextApiRequest, NextApiResponse } from "next";
import { getToken } from "next-auth/jwt";
import invariant from "tiny-invariant";
import { apiEndpointRoot } from "~/app/api-server";

export default async function proxy(req: NextApiRequest, res: NextApiResponse) {
  invariant(req.url !== undefined, "Request did not have url");
  const token = await getToken({ req });
  const realPath = req.url.replace(/^\/api\/v1\//, "/");
  const url = new URL(realPath, apiEndpointRoot);
  console.info("Proxy request to", url.toString());

  const response = await fetch(url, {
    method: req.method,
    body: req.method === "GET" ? undefined : JSON.stringify(req.body),
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization: `Bearer ${token?.accessToken}`,
    },
  });

  console.info("Proxy response", response.status, response.statusText);
  if (response.ok) {
    const data = await response.json();
    return res.status(response.status).json(data);
  }
  return res.status(response.status).json({ error: response.statusText });
}
