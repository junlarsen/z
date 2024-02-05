"use client";

import { signIn } from "next-auth/react";
import { useEffect } from "react";

type PageParams = {
  searchParams: {
    callbackUrl: string | undefined;
  };
};

export default function Page({ searchParams }: PageParams) {
  useEffect(() => {
    signIn("cognito", {
      callbackUrl: searchParams.callbackUrl ?? "/",
    });
  }, [searchParams.callbackUrl]);
}
