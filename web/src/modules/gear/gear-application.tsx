import { Card, Center, GridCol, Text } from "@mantine/core";
import { IconProps } from "@radix-ui/react-icons/dist/types";
import { FC } from "react";
import { Link } from "react-router-dom";

export type GearApplicationProps = {
  icon: FC<IconProps>;
  label: string;
  href: string;
};

export const GearApplication: FC<GearApplicationProps> = ({
  href,
  icon: IconComponent,
  label,
}) => {
  return (
    <GridCol span={4}>
      <Card component={Link} withBorder to={href}>
        <Center>
          <IconComponent width={32} height={32} />
        </Center>
        <Text ta="center" c="dimmed">
          {label}
        </Text>
      </Card>
    </GridCol>
  );
};
