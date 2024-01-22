"use client";

import { Session } from "next-auth";
import {
  SessionProvider as NextSessionProvider,
  signIn,
} from "next-auth/react";
import { FC, PropsWithChildren, useEffect } from "react";

export type SessionProviderProps = {
  session: Session | null;
} & PropsWithChildren;

export const SessionProvider: FC<SessionProviderProps> = ({
  session,
  children,
}) => {
  useEffect(() => {
    if (session !== null) {
      return;
    }
    void signIn("cognito");
  }, [session]);

  return (
    <NextSessionProvider session={session}>{children}</NextSessionProvider>
  );
};
