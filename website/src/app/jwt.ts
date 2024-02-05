import { decode } from "next-auth/jwt";
import { cookies } from "next/headers";

export const getServerToken = async () => {
  const requestCookies = cookies();
  const sessionToken = requestCookies.get("next-auth.session-token");
  if (sessionToken === undefined) {
    return null;
  }
  return await decode({
    token: sessionToken.value,
    secret: process.env.NEXTAUTH_SECRET,
  });
};
