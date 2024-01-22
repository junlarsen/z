"use client";

import { Button, CopyButton, PasswordInput, Stack, Text } from "@mantine/core";
import { ContextModalProps } from "@mantine/modals";
import { FC } from "react";

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
        {({ copied, copy }) => (
          <Button color={copied ? "teal" : "blue"} onClick={copy}>
            {copied ? "Copied" : "Copy"}
          </Button>
        )}
      </CopyButton>
    </Stack>
  );
};
