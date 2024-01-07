import react from "@vitejs/plugin-react-swc";
import { defineConfig } from "vite";

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "^/api/.*": {
        target: "http://localhost:8080/",
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
