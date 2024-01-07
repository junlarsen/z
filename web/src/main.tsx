import "@fontsource/inter/index.css";
import "@fontsource/poppins/index.css";
import "@mantine/core/styles.css";
import "@mantine/dates/styles.css";
import "@mantine/notifications/styles.css";
import "@mantine/spotlight/styles.css";

import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import { MantineProvider } from "./components/mantine-provider.tsx";
import { router } from "./router.tsx";

const root = document.getElementById("root");

if (root === null) {
  throw new Error("Failed to mount application to root");
}

ReactDOM.createRoot(root).render(
  <React.StrictMode>
    <MantineProvider>
      <RouterProvider router={router} />
    </MantineProvider>
  </React.StrictMode>,
);
