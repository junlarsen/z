"use client";

import {
  Button,
  Center,
  LoadingOverlay,
  Stack,
  Text,
  Textarea,
} from "@mantine/core";
import { useMutation, useQuery } from "@tanstack/react-query";
import React from "react";
import { CopyButton } from "~/app/components/copy-button";
import { GradientTitle } from "~/app/components/gradient-title";
import { GoBackRedirect } from "~/app/components/redirect";
import { createApiRequest } from "~/app/http/http";

type PageParams = {
  params: {
    slug: string;
  };
};

export default function OnceSecretPage({ params }: PageParams) {
  const {
    data: secret,
    isLoading,
    isError,
    isSuccess,
  } = useQuery({
    queryFn: async () =>
      createApiRequest(`/secrets/${params.slug}/preview`, "GET", null).then(
        (_) => _.json(),
      ),
    queryKey: ["secret", params.slug],
  });
  const view = useMutation({
    mutationFn: async (slug: string) =>
      createApiRequest(`/secrets/${slug}`, "GET", null).then((_) => _.json()),
  });
  const fmt = new Intl.DateTimeFormat("en", {
    minute: "numeric",
    hour: "numeric",
    weekday: "long",
    day: "numeric",
    month: "long",
    year: "numeric",
  });

  return (
    <Center mih="100vh" p="md" maw={600} mx="auto">
      {isError && (
        <Text
          component="h1"
          size="xl"
          fw="bold"
          variant="gradient"
          gradient={{ from: "indigo", to: "pink", deg: 90 }}
        >
          Secret not found
        </Text>
      )}
      {isLoading && (
        <LoadingOverlay
          visible
          zIndex={1000}
          overlayProps={{ radius: "sm", blur: 2 }}
        />
      )}
      {isSuccess && (
        <Stack>
          <div>
            <GoBackRedirect href="/once" />
            <GradientTitle>You've been sent a shared secret.</GradientTitle>
          </div>
          <Text>
            The secret expires on {fmt.format(new Date(secret.expiresAt))}, at
            which point it will no longer be possible to view it.
          </Text>
          <Text c="dimmed">
            The secret might also have limited views, meaning it can only be
            viewed a certain amount of times.
          </Text>
          {view.isIdle && (
            <Button onClick={() => view.mutate(params.slug)}>
              View secret
            </Button>
          )}
          {view.isError && <div>Error: {view.error.message}</div>}
          {view.isSuccess && (
            <>
              <Textarea
                label="Secret contents"
                rows={12}
                readOnly
                value={view.data.secret}
              />
              <CopyButton value={view.data.secret}>
                {({ copied }) => (copied ? "Copied" : "Copy")}
              </CopyButton>
            </>
          )}
        </Stack>
      )}
    </Center>
  );
}
