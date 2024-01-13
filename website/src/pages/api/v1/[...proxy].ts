import { NextApiRequest, NextApiResponse } from "next";
import { getToken } from "next-auth/jwt";
import invariant from "tiny-invariant";

const endpoint =
  process.env.NEXT_PUBLIC_API_ENDPOINT ?? "http://localhost:8080";

export default async function proxy(req: NextApiRequest, res: NextApiResponse) {
  invariant(req.url !== undefined, "Request did not have url");
  const token = await getToken({ req });
  const realPath = req.url.replace(/^\/api\/v1\//, "/api/");
  const url = new URL(realPath, endpoint);

  console.info("Proxy request to", url.toString(), token?.accessToken);

  const response = await fetch(url, {
    method: req.method,
    body: req.method === "GET" ? undefined : req.body,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
  });

  const data = await response.json();
  res.status(response.status).json(data);
}
