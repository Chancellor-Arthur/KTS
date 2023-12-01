import { ButtonProps, Button as DefaultButton } from "@mantine/core";
import { FC } from "react";

const Button: FC<ButtonProps> = ({ ...props }) => {
  return (
    <DefaultButton
      style={{
        backgroundColor: "rgb(var(--primary))",
        color: "rgb(var(--background))",
      }}
      {...props}
    />
  );
};

export { Button };
