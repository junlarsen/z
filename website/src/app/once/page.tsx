"use client"

import {Button, Center, NumberInput, Stack, Textarea, Text} from "@mantine/core";
import { DateTimePicker } from "@mantine/dates";
import {addMinutes} from "date-fns"

export default function OncePage() {
    const now = new Date()
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
              Once - a one-time secret sharing service
          </Text>
        <Textarea label="Secret content" placeholder="I love you" description="The secret value you wish to share." rows={12} />
        <DateTimePicker
          label="Expiration time"
          description="This content will be deleted after this time. Default 60 minutes"
          defaultValue={addMinutes(now, 60)}
          minDate={now}
        />
          <NumberInput label="Maximum views" description="Expires after given amount of views" defaultValue={1} min={1} max={100} />
        <Button type="submit">Generate sharing link</Button>
      </Stack>
    </Center>
  );
}
