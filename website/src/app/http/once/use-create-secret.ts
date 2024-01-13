import { useMutation } from "@tanstack/react-query";
import { createApiRequest } from "~/app/http/http";

export type CreateSecretCommandInput = {
  secret: string;
  expiresAt: Date;
  remainingViews: number;
};

export const useCreateSecret = () =>
  useMutation({
    mutationFn: async (input: CreateSecretCommandInput) =>
      createApiRequest("/secret", "POST", input),
  });
