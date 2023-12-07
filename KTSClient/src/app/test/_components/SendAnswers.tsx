"use client";

import { TestContext } from "@/app/test/_providers/TestProvider";
import { clientFetchData } from "@/utils";
import { Button } from "@mantine/core";
import cn from "classnames";
import { useRouter } from "next/navigation";
import { FC, useCallback, useContext } from "react";

export const SendAnswers: FC = () => {
  const { answersCount, answers } = useContext(TestContext);
  const router = useRouter();

  const sendAnswers = useCallback(async () => {
    try {
      await clientFetchData("/api/answers", "POST", {
        answerIds: Array.from(answers.values()).filter(Boolean),
      });
      router.push("/account");
    } catch (err) {
      console.error(err);
    }
  }, [answers, router]);

  return (
    <Button
      className={cn(
        answersCount === answers.size
          ? "bg-primary text-background hover:bg-accent"
          : "bg-gray-400 text-background"
      )}
      disabled={answersCount !== answers.size}
      onClick={sendAnswers}
    >
      Отправить
    </Button>
  );
};
