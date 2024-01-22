"use client";

import {
  MantineProvider as MantineCoreProvider,
  createTheme,
} from "@mantine/core";
import { ModalsProvider } from "@mantine/modals";
import { Notifications } from "@mantine/notifications";
import { FC, PropsWithChildren } from "react";
import { ShareModal } from "~/app/once/share-modal";

const theme = createTheme({
  primaryColor: "violet",
  fontFamily: "Inter, sans-serif",
  headings: { fontFamily: "Poppins, sans-serif" },
});

const modals = {
  "once/share-secret": ShareModal,
} as const;

declare module "@mantine/modals" {
  export interface MantineModalsOverride {
    modals: typeof modals;
  }
}

export const MantineProvider: FC<PropsWithChildren> = ({ children }) => {
  return (
    <MantineCoreProvider theme={theme}>
      <Notifications />
      <ModalsProvider modals={modals}>{children}</ModalsProvider>
    </MantineCoreProvider>
  );
};
