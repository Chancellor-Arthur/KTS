"use client";

import { InputWrapper } from "@/components";
import { clientFetchData } from "@/utils";
import { Button, Input, Text } from "@mantine/core";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { FC, useCallback } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
interface RegisterFormValue {
  username: string;
  password: string;
}

export const RegisterForm: FC = () => {
  const { register, handleSubmit } = useForm<RegisterFormValue>();
  const navigation = useRouter();

  const submitHandler = useCallback<SubmitHandler<RegisterFormValue>>(
    async (values) => {
      try {
        await clientFetchData("/api/auth/registration", "POST", values);
        navigation.push("/login");
      } catch (err) {
        console.error(err);
      }
    },
    [navigation]
  );

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
        variant="filled"
        type="submit"
        className="bg-primary text-background hover:bg-accent"
      >
        Зарегистрироваться
      </Button>
      <Link href="/login">
        <Text td="underline" className="text-text">
          Вход
        </Text>
      </Link>
    </form>
  );
};
