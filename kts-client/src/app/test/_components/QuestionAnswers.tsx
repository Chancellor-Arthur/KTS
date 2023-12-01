"use client";

import { TestContext } from "@/app/test/_providers/TestProvider";
import { OutputAnswer, OutputQuestion } from "@/utils/types";
import { Radio } from "@mantine/core";
import { FC, useContext } from "react";

export interface QuestionAnswersProps {
  answers: OutputAnswer[];
  questionId: OutputQuestion["id"];
}

export const QuestionAnswers: FC<QuestionAnswersProps> = ({
  questionId,
  answers: answerVariants,
}) => {
  const { answerTheQuestion, answers } = useContext(TestContext);
  return (
    <>
      {answerVariants.map((answer) => (
        <Radio
          key={answer.id}
          label={answer.answer}
          checked={answers.get(questionId) === answer.id}
          onChange={() => {
            answerTheQuestion(questionId, answer.id);
          }}
        />
      ))}
    </>
  );
};
