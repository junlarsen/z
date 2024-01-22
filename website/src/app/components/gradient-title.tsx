import { Text, TextProps } from "@mantine/core";
import React, { FC, PropsWithChildren } from "react";

export type GradientTitleProps = PropsWithChildren & TextProps;

export const GradientTitle: FC<GradientTitleProps> = ({
  children,
  ...props
}) => {
  return (
    <Text
      component="h1"
      size="xl"
      fw="bold"
      variant="gradient"
      gradient={{ from: "indigo", to: "pink", deg: 90 }}
      {...props}
    >
      {children}
    </Text>
  );
};
