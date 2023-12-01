"use client";

import { TestContext } from "@/app/test/_providers/TestProvider";
import cn from "classnames";
import { FC, useContext } from "react";

interface AnswersCounterProps {
  className?: string;
}

export const AnswersCounter: FC<AnswersCounterProps> = ({ className }) => {
  const { answers, answersCount } = useContext(TestContext);
  return (
    <div className={cn(["text-text", className])}>
      Дан ответ на {answersCount} из {answers.size} вопросов
    </div>
  );
};
