export interface OutputAnswer {
  id: string;
  answer: string;
}

export interface OutputQuestion {
  id: string;
  question: string;
  answers: OutputAnswer[];
}

interface OutputUserAnswerVariant extends OutputAnswer {
  correct: boolean;
}

interface OutputUserQuestionAnswer
  extends Pick<OutputQuestion, "id" | "question"> {
  answers: OutputUserAnswerVariant[];
}

export interface OutputUserAnswer {
  questionAnswer: OutputUserQuestionAnswer;
  userAnswer: OutputAnswer;
}
