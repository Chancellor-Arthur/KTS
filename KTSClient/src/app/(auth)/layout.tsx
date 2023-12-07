import { PropsWithChildren } from "@/utils";
import { Box } from "@mantine/core";
import { FC } from "react";

const AuthLayout: FC<PropsWithChildren> = ({ children }) => (
  <Box className="flex justify-center p-4 items-center h-full">{children}</Box>
);

export default AuthLayout;
