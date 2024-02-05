import { CognitoJwtVerifier } from "aws-jwt-verify";
import { getToken } from "next-auth/jwt";
import { NextRequest, NextResponse } from "next/server";

const verifier = CognitoJwtVerifier.create({
  userPoolId: process.env.COGNITO_USER_POOL_ID,
  clientId: process.env.COGNITO_CLIENT_ID,
  tokenUse: "access",
});

export async function middleware(request: NextRequest) {
  if (
    request.url.includes("/_next/static") ||
    request.url.includes("/api/auth") ||
    request.url.includes("/session/refresh")
  ) {
    return;
  }
  const token = await getToken({ req: request });
  if (token === null) {
    return;
  }

  try {
    const payload = await verifier.verify(token.accessToken);
    console.log(
      `Validated Cognito issued JWT for subject ${payload.sub} at path ${request.url}`,
    );
  } catch (error) {
    console.error("Failed to validate Cognito issued JWT", error);
    const url = request.nextUrl.clone();
    url.pathname = "/session/refresh";
    url.searchParams.set("callbackUrl", request.nextUrl.pathname);
    return NextResponse.redirect(url);
  }
}
