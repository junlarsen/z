import {
    Route,
    createBrowserRouter,
    createRoutesFromElements, Outlet,
} from "react-router-dom";
import { GearPage } from "./modules/gear/gear-page.tsx";
import { OncePage } from "./modules/once/once-page.tsx";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<GearPage />} />
      <Route path="/once" element={<OncePage />} />
    </>,
  ),
);
