"use client";

import { Affix, Button } from "@mantine/core";
import { IconLogin, IconLogout } from "@tabler/icons-react";
import { signIn, signOut, useSession } from "next-auth/react";
import { FC } from "react";

export const AffixedLogin: FC = () => {
  const session = useSession();
  const handler =
    session.status !== "unauthenticated"
      ? () => signOut()
      : () => signIn("cognito");

  return (
    <Affix position={{ bottom: 20, right: 20 }}>
      <Button
        variant="outline"
        onClick={handler}
        rightSection={
          session.status !== "unauthenticated" ? <IconLogin /> : <IconLogout />
        }
      >
        {session.status !== "authenticated"
          ? "Log in"
          : `Logged in as ${session.data.user.email}`}
      </Button>
    </Affix>
  );
};
