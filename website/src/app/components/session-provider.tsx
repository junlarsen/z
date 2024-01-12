"use client";

import { Session } from "next-auth";
import { SessionProvider as NextSessionProvider } from "next-auth/react";
import { FC, PropsWithChildren } from "react";

export type SessionProviderProps = {
  session: Session | null;
} & PropsWithChildren;

export const SessionProvider: FC<SessionProviderProps> = ({
  session,
  children,
}) => {
  return (
    <NextSessionProvider session={session}>{children}</NextSessionProvider>
  );
};
