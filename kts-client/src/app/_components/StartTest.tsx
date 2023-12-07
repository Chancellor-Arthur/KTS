"use client";

import { Button } from "@mantine/core";
import { useRouter } from "next/navigation";
import { FC } from "react";

export const StartTest: FC = () => {
  const router = useRouter();
  return (
    <Button
      className="bg-primary text-background hover:bg-accent"
      onClick={() => router.push("/test")}
    >
      Начать тест
    </Button>
  );
};
