import { AnswersCounter } from "@/app/test/_components/AnswersCounter";
import { QuestionAnswers } from "@/app/test/_components/QuestionAnswers";
import { SendAnswers } from "@/app/test/_components/SendAnswers";
import { TestContextProvider } from "@/app/test/_providers/TestProvider";
import { serverFetchData } from "@/utils/server";
import { OutputQuestion } from "@/utils/types";
import { Button, Radio } from "@mantine/core";

const TestPage = async () => {
  const questions = await serverFetchData<OutputQuestion[]>(
    "/questions/test",
    "GET"
  );

  if (!questions) return null;

  return (
    <TestContextProvider questionIds={questions.map((question) => question.id)}>
      <div className="flex items-end w-100">
        <AnswersCounter className="sticky bottom-4 w-1/3 text-right" />
        <div className="text-text flex flex-col gap-6 p-5 max-w-lg w-1/3">
          <h1 className="text-xl">Тестирование</h1>
          {questions?.map((question, questionIndex) => (
            <div
              key={question.id}
              className="border border-primary rounded-md p-4 text-base"
            >
              <span>
                {questionIndex + 1}. {question.question}
              </span>
              <div className="p-4 flex flex-col gap-2 text-sm">
                <QuestionAnswers
                  questionId={question.id}
                  answers={question.answers}
                />
              </div>
            </div>
          ))}
        </div>
        <div className="sticky bottom-4 w-1/3">
          <SendAnswers />
        </div>
      </div>
    </TestContextProvider>
  );
};

export default TestPage;
