"use client";

import { UserContext } from "@/app/_providers/UserContextProvider";
import { InputWrapper } from "@/components";
import { clientFetchData } from "@/utils";
import { Button, Input } from "@mantine/core";
import { useRouter } from "next/navigation";
import { FC, useCallback, useContext, useEffect } from "react";
import { SubmitHandler, useForm } from "react-hook-form";

interface LoginFormValues {
  username: string;
  password: string;
}

export const LoginForm: FC = () => {
  const { register, handleSubmit } = useForm<LoginFormValues>();
  const { changeLoggedStatus, isLoggedIn } = useContext(UserContext);
  const navigation = useRouter();

  const submitHandler = useCallback<SubmitHandler<LoginFormValues>>(
    async ({ username, password }) => {
      try {
        const client = await clientFetchData("/api/auth/login", "POST", {
          username,
          password,
        });
        if (client) changeLoggedStatus(true);
      } catch (err) {
        console.error(err);
      }
    },
    [changeLoggedStatus]
  );

  useEffect(() => {
    if (isLoggedIn) navigation.push("/");
  }, [isLoggedIn, navigation]);

  return (
    <form
      onSubmit={handleSubmit(submitHandler)}
      className="flex flex-col gap-2"
    >
      <InputWrapper label="Логин">
        <Input {...register("username")} />
      </InputWrapper>
      <InputWrapper label="Пароль">
        <Input type="password" {...register("password")} />
      </InputWrapper>
      <Button
        type="submit"
        variant="filled"
        className="bg-primary text-background hover:bg-accent"
      >
        Войти
      </Button>
    </form>
  );
};
