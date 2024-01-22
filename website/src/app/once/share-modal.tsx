"use client";

import { Button, PasswordInput, Stack, Text } from "@mantine/core";
import { ContextModalProps } from "@mantine/modals";
import { FC } from "react";
import { CopyButton } from "~/app/components/copy-button";

export type ShareModalContext = {
  slug: string;
};

export const ShareModal: FC<ContextModalProps<ShareModalContext>> = ({
  innerProps,
}) => {
  const location = new URL(`/once/${innerProps.slug}`, document.baseURI).href;
  return (
    <Stack>
      <Text>
        Your secret value has been created. You can now share it using the
        following link
      </Text>
      <PasswordInput readOnly value={location} />
      <CopyButton value={location}>
        {({ copied }) => (copied ? "Copied" : "Copy")}
      </CopyButton>
    </Stack>
  );
};
