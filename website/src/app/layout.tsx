import "@mantine/charts/styles.css";
import { ColorSchemeScript } from "@mantine/core";
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
import { QueryProvider } from "~/app/components/query-provider";
import { SessionProvider } from "~/app/components/session-provider";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "~",
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
          <QueryProvider>
            <MantineProvider>
              {children}
              <AffixedLogin />
            </MantineProvider>
          </QueryProvider>
        </SessionProvider>
      </body>
    </html>
  );
}
