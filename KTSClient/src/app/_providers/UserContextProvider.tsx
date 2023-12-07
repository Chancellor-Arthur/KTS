"use client";

import { PropsWithChildren, clientFetchData } from "@/utils";
import { usePathname, useRouter } from "next/navigation";
import { FC, createContext, useCallback, useEffect, useState } from "react";

interface UserContextValues {
  isLoggedIn: boolean;
  changeLoggedStatus: (status: boolean) => void;
  logout: () => Promise<void>;
}

export const UserContext = createContext<UserContextValues>(
  {} as UserContextValues
);

interface UserContextProvider
  extends PropsWithChildren,
    Pick<UserContextValues, "isLoggedIn"> {}

export const UserContextProvider: FC<UserContextProvider> = ({
  children,
  isLoggedIn: initialIsLoggedIn,
}) => {
  const [isLoggedIn, setIsLoggedIn] =
    useState<UserContextValues["isLoggedIn"]>(initialIsLoggedIn);
  const router = useRouter();
  const pathName = usePathname();

  const changeLoggedStatus = useCallback<
    UserContextValues["changeLoggedStatus"]
  >((status) => {
    setIsLoggedIn(status);
  }, []);

  useEffect(() => {
    if (!["/login", "/register"].includes(pathName) && !isLoggedIn)
      router.push("/login");
  }, [isLoggedIn, pathName, router]);

  const logout = useCallback(async () => {
    try {
      await clientFetchData("/api/auth/logout", "POST");
      changeLoggedStatus(false);
    } catch (err) {
      console.error(err);
    }
  }, [changeLoggedStatus]);

  return (
    <UserContext.Provider value={{ isLoggedIn, changeLoggedStatus, logout }}>
      {children}
    </UserContext.Provider>
  );
};
