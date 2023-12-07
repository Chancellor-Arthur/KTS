import { RegisterForm } from "@/app/(auth)/register/_components/RegisterForm";
import { Box } from "@mantine/core";
import { FC } from "react";

const RegisterPage: FC = () => {
  return (
    <Box className="max-w-2xl border border-primary p-3 rounded-md flex flex-col gap-3">
      <RegisterForm />
    </Box>
  );
};

export default RegisterPage;
