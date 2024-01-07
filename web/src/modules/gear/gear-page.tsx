import { Center, Grid, Stack, Text } from "@mantine/core";
import {
  ActivityLogIcon,
  BackpackIcon,
  GridIcon,
  ListBulletIcon,
  LockClosedIcon,
  MixIcon,
} from "@radix-ui/react-icons";
import { FC } from "react";
import { GearApplication } from "./gear-application.tsx";

export const GearPage: FC = () => {
  return (
    <Center mih="100vh" p="md">
      <Stack>
        <Text
          ta="center"
          component="h1"
          size="xl"
          fw="bold"
          variant="gradient"
          gradient={{ from: "indigo", to: "pink", deg: 90 }}
        >
          Applications
        </Text>
        <Grid gutter="md">
          <GearApplication icon={LockClosedIcon} label="Once" href="/once" />
          <GearApplication
            icon={ActivityLogIcon}
            label="Logged"
            href="/logged"
          />
          <GearApplication
            icon={BackpackIcon}
            label="Commute"
            href="/commute"
          />
          <GearApplication icon={ListBulletIcon} label="Todo" href="/todo" />
          <GearApplication icon={MixIcon} label="Dashboard" href="/dashboard" />
          <GearApplication icon={GridIcon} label="TBA" href="#" />
          <GearApplication icon={GridIcon} label="TBA" href="#" />
          <GearApplication icon={GridIcon} label="TBA" href="#" />
          <GearApplication icon={GridIcon} label="TBA" href="#" />
        </Grid>
      </Stack>
    </Center>
  );
};
