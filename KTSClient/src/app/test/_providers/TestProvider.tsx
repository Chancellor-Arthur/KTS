"use client";

import { PropsWithChildren } from "@/utils";
import { OutputAnswer, OutputQuestion } from "@/utils/types";
import { FC, createContext, useCallback, useMemo, useState } from "react";
type AnswersMap = Map<OutputQuestion["id"], OutputAnswer["id"] | undefined>;

interface TestContextValues {
  answers: AnswersMap;
  answerTheQuestion: (
    questionId: OutputQuestion["id"],
    answerId: OutputAnswer["id"]
  ) => void;
  answersCount: number;
}

export const TestContext = createContext<TestContextValues>(
  {} as TestContextValues
);

export interface TestContextProvider extends PropsWithChildren {
  questionIds: OutputQuestion["id"][];
}

export const TestContextProvider: FC<TestContextProvider> = ({
  children,
  questionIds,
}) => {
  const [answers, setAnswers] = useState<AnswersMap>(() => {
    const answersMap: AnswersMap = new Map();
    questionIds.forEach((questionId) => {
      answersMap.set(questionId, undefined);
    });

    return answersMap;
  });

  const answerTheQuestion = useCallback<TestContextValues["answerTheQuestion"]>(
    (questionId, answerId) => {
      setAnswers((answers) => {
        const newAnswers = new Map(answers);
        newAnswers.set(questionId, answerId);
        return newAnswers;
      });
    },
    []
  );

  const answersCount = useMemo(
    () => Array.from(answers.values()).filter(Boolean).length,
    [answers]
  );

  const contextValues = useMemo<TestContextValues>(
    () => ({ answers, answerTheQuestion, answersCount }),
    [answerTheQuestion, answers, answersCount]
  );

  return (
    <TestContext.Provider value={contextValues}>
      {children}
    </TestContext.Provider>
  );
};
