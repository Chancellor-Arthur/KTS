import { LoginForm } from "@/app/(auth)/login/_components/LoginForm";
import { Box, Text } from "@mantine/core";
import Link from "next/link";
import { FC } from "react";

const LoginPage: FC = () => {
  return (
    <Box className="max-w-2xl border border-primary p-3 rounded-md flex flex-col gap-3">
      <LoginForm />
      <Link href="/register">
        <Text td="underline" className="text-text">
          Регистрация
        </Text>
      </Link>
    </Box>
  );
};

export default LoginPage;
