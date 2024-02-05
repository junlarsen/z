"use client";

import {
  Button,
  Center,
  Group,
  NumberInput,
  Stack,
  Textarea,
} from "@mantine/core";
import { DateTimePicker } from "@mantine/dates";
import { modals } from "@mantine/modals";
import { useMutation } from "@tanstack/react-query";
import { addMinutes } from "date-fns";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { GradientTitle } from "~/app/components/gradient-title";
import { GoBackRedirect } from "~/app/components/redirect";
import { createApiRequest } from "~/app/http/http";

export type CreateSecretCommandInput = {
  secret: string;
  expiresAt: Date;
  remainingViews: number;
};

export default function OncePage() {
  const createSecret = useMutation({
    mutationFn: async (input: CreateSecretCommandInput) =>
      createApiRequest("/secrets", "POST", input),
    onSuccess: async (response) => {
      const json = await response.json();
      modals.openContextModal({
        modal: "once/share-secret",
        title: "Share secret",
        innerProps: {
          slug: json.slug,
        },
      });
    },
  });
  const now = new Date();
  const { register, handleSubmit, reset, control } =
    useForm<CreateSecretCommandInput>({
      defaultValues: {
        expiresAt: addMinutes(now, 60),
        remainingViews: 1,
      },
    });
  const onSubmit = handleSubmit((data) => {
    reset();
    createSecret.mutate(data);
  });
  return (
    <Center mih="100vh" p="md" maw={600} mx="auto">
      <Stack>
        <div>
          <GoBackRedirect href="/" />
          <GradientTitle>
            Once - a one-time secret sharing service
          </GradientTitle>
        </div>
        <form onSubmit={onSubmit}>
          <Stack>
            <Textarea
              label="Secret content"
              placeholder="I love you"
              description="The secret value you wish to share."
              rows={12}
              required
              {...register("secret")}
            />
            <Group>
              <Controller
                render={({ field, fieldState }) => (
                  <DateTimePicker
                    label="Expiration time"
                    description="This content will be deleted after this time. Default 60 minutes."
                    minDate={now}
                    value={field.value}
                    required
                    error={fieldState.error?.message}
                    onChange={field.onChange}
                  />
                )}
                name="expiresAt"
                control={control}
              />
              <Controller
                render={({ field, fieldState }) => (
                  <NumberInput
                    label="Maximum views"
                    description="Expires after given amount of views."
                    value={field.value}
                    onChange={field.onChange}
                    min={1}
                    max={100}
                    error={fieldState.error?.message}
                    required
                  />
                )}
                name="remainingViews"
                control={control}
              />
            </Group>
            <Button type="submit">Generate sharing link</Button>
          </Stack>
        </form>
      </Stack>
    </Center>
  );
}
