import {Card, Center, Text} from "@mantine/core";
import { FC } from "react";

export const OncePage: FC = () => {
  return <Center mih="100vh">
      <Card withBorder>
          <Text
              ta="center"
              component="h1"
              size="xl"
              fw="bold"
              variant="gradient"
              gradient={{ from: "indigo", to: "pink", deg: 90 }}
          >
              Once - a one-time-link sharing application
          </Text>
      </Card>
  </Center>;
};
