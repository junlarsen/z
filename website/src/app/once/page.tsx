"use client";

import {
  Anchor,
  Button,
  Center,
  Group,
  NumberInput,
  Stack,
  Text,
  Textarea,
} from "@mantine/core";
import { DateTimePicker } from "@mantine/dates";
import { modals } from "@mantine/modals";
import { IconArrowLeft } from "@tabler/icons-react";
import { useMutation } from "@tanstack/react-query";
import { addMinutes } from "date-fns";
import Link from "next/link";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { createApiRequest } from "~/app/http/http";

export type CreateSecretCommandInput = {
  secret: string;
  expiresAt: Date;
  remainingViews: number;
};

export default function OncePage() {
  const createSecret = useMutation({
    mutationFn: async (input: CreateSecretCommandInput) =>
      createApiRequest("/secret", "POST", input),
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
          <Anchor component={Link} href="/">
            <IconArrowLeft />
          </Anchor>
          <Text
            component="h1"
            size="xl"
            fw="bold"
            variant="gradient"
            gradient={{ from: "indigo", to: "pink", deg: 90 }}
          >
            Once - a one-time secret sharing service
          </Text>
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
