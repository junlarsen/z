"use client";

import {
  MantineProvider as MantineCoreProvider,
  createTheme,
} from "@mantine/core";
import { ModalsProvider } from "@mantine/modals";
import { Notifications } from "@mantine/notifications";
import { FC, PropsWithChildren } from "react";

const theme = createTheme({
  primaryColor: "violet",
  fontFamily: "Inter, sans-serif",
  headings: { fontFamily: "Poppins, sans-serif" },
});

const modals = {};

export const MantineProvider: FC<PropsWithChildren> = ({ children }) => {
  return (
    <MantineCoreProvider theme={theme}>
      <Notifications />
      <ModalsProvider modals={modals}>{children}</ModalsProvider>
    </MantineCoreProvider>
  );
};
