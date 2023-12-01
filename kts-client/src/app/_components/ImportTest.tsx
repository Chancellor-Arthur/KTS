"use client";

import { clientFetchData } from "@/utils";
import { Button } from "@mantine/core";
import { FC, useCallback } from "react";

export const ImportTest: FC = () => {
  const handleButtonClick = useCallback(async () => {
    try {
      await clientFetchData("/api/questions/import", "POST");
    } catch (err) {
      console.error(err);
    }
  }, []);
  return (
    <Button
      className="bg-primary text-background hover:bg-accent"
      onClick={handleButtonClick}
    >
      Импорт вопросов из файла
    </Button>
  );
};
