import { serverFetchData } from "@/utils/server";
import { OutputUserAnswer } from "@/utils/types";
import { Radio } from "@mantine/core";
import cn from "classnames";

const AccountPage = async () => {
  const answers = await serverFetchData<OutputUserAnswer[]>("/answers", "GET");

  return (
    <div className="text-text w-100 p-8 flex flex-col gap-8">
      <h1 className="text-lg">Личный кабинет</h1>
      <div className="flex flex-col gap-4">
        {(answers ?? []).map((answer) => {
          const checkedAnswer = answer.questionAnswer.answers.find(
            (answerVariant) => answerVariant.id === answer.userAnswer.id
          );
          if (!checkedAnswer) return null;

          return (
            <div
              key={answer.questionAnswer.id}
              className="border border-primary p-4 rounded-md flex flex-col gap-2"
            >
              <span>{answer.questionAnswer.question}</span>
              <div className="flex flex-col gap-2 ml-4">
                {answer.questionAnswer.answers.map((answerVariant) => (
                  <Radio
                    key={answerVariant.id}
                    label={answerVariant.answer}
                    checked={answerVariant.id === checkedAnswer.id}
                    className={cn([answerVariant.correct && "text-success", answerVariant.id === checkedAnswer.id && !answerVariant.correct && 'text-danger'])}
                    readOnly
                  />
                ))}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default AccountPage;
