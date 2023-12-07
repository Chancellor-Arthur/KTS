"use client";

import { useRouter } from "next/navigation";
import { FC, useEffect } from "react";

export const RedirectComponent: FC = () => {
  const { push } = useRouter();
  useEffect(() => {
    push("/login");
  }, [push]);
  return null;
};
