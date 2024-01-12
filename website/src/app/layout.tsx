import "@mantine/charts/styles.css";
import { ColorSchemeScript, Flex, Stack } from "@mantine/core";
import "@mantine/core/styles.css";
import "@mantine/dates/styles.css";
import "@mantine/notifications/styles.css";
import "@mantine/spotlight/styles.css";
import type { Metadata } from "next";
import { getServerSession } from "next-auth";
import { Inter } from "next/font/google";
import { PropsWithChildren } from "react";
import { AffixedLogin } from "~/app/components/affixed-login";
import { MantineProvider } from "~/app/components/mantine-provider";
import { SessionProvider } from "~/app/components/session-provider";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Pivot",
  description: "Home base of operations",
};

export default async function RootLayout({ children }: PropsWithChildren) {
  const session = await getServerSession();
  return (
    <html lang="en" dir="ltr">
      <head>
        <ColorSchemeScript />
      </head>
      <body>
        <SessionProvider session={session}>
          <MantineProvider>
            {children}
            <AffixedLogin />
          </MantineProvider>
        </SessionProvider>
      </body>
    </html>
  );
}
