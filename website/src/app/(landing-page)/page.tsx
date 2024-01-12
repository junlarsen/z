import { Center, Grid, Stack, Text } from "@mantine/core";
import {
  IconActivity,
  IconBusStop,
  IconGrid3x3,
  IconListCheck,
  IconShieldLock,
} from "@tabler/icons-react";
import { GearApplication } from "~/app/(landing-page)/gear-application";

export default function LandingPage() {
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
          <GearApplication
            icon={<IconShieldLock />}
            label="Once"
            href="/once"
          />
          <GearApplication
            icon={<IconActivity />}
            label="Logged"
            href="/logged"
          />
          <GearApplication
            icon={<IconBusStop />}
            label="Commute"
            href="/commute"
          />
          <GearApplication icon={<IconListCheck />} label="Todo" href="/todo" />
          <GearApplication icon={<IconGrid3x3 />} label="TBA" href="#" />
          <GearApplication icon={<IconGrid3x3 />} label="TBA" href="#" />
          <GearApplication icon={<IconGrid3x3 />} label="TBA" href="#" />
          <GearApplication icon={<IconGrid3x3 />} label="TBA" href="#" />
          <GearApplication icon={<IconGrid3x3 />} label="TBA" href="#" />
        </Grid>
      </Stack>
    </Center>
  );
}
