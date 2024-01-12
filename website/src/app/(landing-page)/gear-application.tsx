import { Card, Center, GridCol, Text } from "@mantine/core";
import Link from "next/link";
import { FC, ReactNode } from "react";

export type GearApplicationProps = {
  icon: ReactNode;
  label: string;
  href: string;
};

export const GearApplication: FC<GearApplicationProps> = ({
  href,
  icon,
  label,
}) => {
  return (
    <GridCol span={4}>
      <Card component={Link} withBorder href={href}>
        <Center>{icon}</Center>
        <Text ta="center" c="dimmed">
          {label}
        </Text>
      </Card>
    </GridCol>
  );
};
