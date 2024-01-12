import NextAuth, { DefaultSession, DefaultUser, User } from "next-auth";
import CognitoProvider from "next-auth/providers/cognito";

declare global {
  namespace NodeJS {
    interface ProcessEnv {
      COGNITO_CLIENT_ID: string;
      COGNITO_CLIENT_SECRET: string;
      COGNITO_CLIENT_ISSUER: string;
    }
  }
}

declare module "next-auth" {
  interface Session extends DefaultSession {
    user: User;
    sub: string;
    id: string;
  }

  interface User extends DefaultUser {
    id: string;
    name: string;
    email: string;
    image?: string;
  }
}

export default NextAuth({
  providers: [
    CognitoProvider({
      clientId: process.env.COGNITO_CLIENT_ID,
      clientSecret: process.env.COGNITO_CLIENT_SECRET,
      issuer: process.env.COGNITO_CLIENT_ISSUER,
      checks: "nonce",
      profile: (profile): User => ({
        id: profile.sub,
        name: `${profile.given_name} ${profile.family_name}`,
        email: profile.email,
        image: profile.picture ?? undefined,
      }),
    }),
  ],
  session: {
    strategy: "jwt",
  },
});