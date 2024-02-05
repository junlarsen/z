import NextAuth, {
  DefaultSession,
  DefaultUser,
  NextAuthOptions,
  User,
} from "next-auth";
import { DefaultJWT, JWT } from "next-auth/jwt";
import CognitoProvider from "next-auth/providers/cognito";

declare global {
  namespace NodeJS {
    interface ProcessEnv {
      COGNITO_CLIENT_ID: string;
      COGNITO_CLIENT_SECRET: string;
      COGNITO_CLIENT_ISSUER: string;
      COGNITO_USER_POOL_ID: string;

      NEXTAUTH_SECRET: string;
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
    email: string;
  }
}

declare module "next-auth/jwt" {
  interface JWT extends DefaultJWT {
    accessToken: string;
  }
}

export const authOptions = {
  providers: [
    CognitoProvider({
      clientId: process.env.COGNITO_CLIENT_ID,
      clientSecret: process.env.COGNITO_CLIENT_SECRET,
      issuer: process.env.COGNITO_CLIENT_ISSUER,
      checks: "nonce",
      profile: (profile): User => ({
        id: profile.sub,
        email: profile.email,
      }),
    }),
  ],
  callbacks: {
    jwt: async ({ token, user, account }): Promise<JWT> => {
      if (account?.access_token) {
        token.accessToken = account.access_token;
      }
      return token;
    },
  },
  session: {
    strategy: "jwt",
  },
} satisfies NextAuthOptions;

export default NextAuth(authOptions);
