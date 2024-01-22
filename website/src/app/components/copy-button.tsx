import {
  Button,
  CopyButton as MantineCopyButton,
  CopyButtonProps as MantineCopyButtonProps,
} from "@mantine/core";
import { FC, ReactNode } from "react";

export type CopyButtonProps = {
  value: string;
  children: ({ copied }: { copied: boolean }) => ReactNode;
} & Omit<MantineCopyButtonProps, "children">;

export const CopyButton: FC<CopyButtonProps> = ({ children, ...props }) => {
  return (
    <MantineCopyButton {...props}>
      {({ copied, copy }) => (
        <Button color={copied ? "grape" : "violet"} onClick={copy}>
          {children({ copied })}
        </Button>
      )}
    </MantineCopyButton>
  );
};
