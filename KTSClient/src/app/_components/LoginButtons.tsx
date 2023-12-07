"use client";

import { UserContext } from "@/app/_providers/UserContextProvider";
import { Button } from "@mantine/core";
import { useRouter } from "next/navigation";
import { FC, useCallback, useContext } from "react";

export const LoginButtons: FC = () => {
  const { isLoggedIn, logout } = useContext(UserContext);
  const navigation = useRouter();

  const handleLoginClick = useCallback(() => {
    if (isLoggedIn) logout();
    else navigation.push("/login");
  }, [isLoggedIn, logout, navigation]);

  const handleRegisterClick = useCallback(async () => {
    navigation.push("/register");
  }, [navigation]);

  return (
    <div className="flex gap-2">
      {!isLoggedIn && (
        <Button
          className="bg-primary text-background hover:bg-accent"
          onClick={handleRegisterClick}
        >
          Регистрация
        </Button>
      )}
      <Button
        className="bg-primary text-background hover:bg-accent"
        onClick={handleLoginClick}
      >
        {isLoggedIn ? "Выйти" : "Войти"}
      </Button>
    </div>
  );
};
