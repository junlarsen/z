import { Anchor } from "@mantine/core";
import { IconArrowLeft } from "@tabler/icons-react";
import Link from "next/link";
import React, { FC } from "react";

export type GoBackRedirectProps = {
  href: string;
};

export const GoBackRedirect: FC<GoBackRedirectProps> = ({ href }) => {
  return (
    <Anchor component={Link} href={href}>
      <IconArrowLeft />
    </Anchor>
  );
};
