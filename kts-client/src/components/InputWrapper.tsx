import {
  InputWrapper as DefaultInputWrapper,
  InputWrapperProps,
} from "@mantine/core";
import cn from "classnames";
import { FC } from "react";

const InputWrapper: FC<InputWrapperProps> = ({ className, ...props }) => {
  return (
    <DefaultInputWrapper className={cn("text-text", className)} {...props} />
  );
};

export { InputWrapper };
