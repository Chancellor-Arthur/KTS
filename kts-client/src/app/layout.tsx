import { UserContextProvider } from "@/app/_providers/UserContextProvider";
import { serverFetchData } from "@/utils/server";
import { Button, ColorSchemeScript, MantineProvider } from "@mantine/core";
import "@mantine/core/styles.css";
import cn from "classnames";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { LoginButtons } from "@/app/_components/LoginButtons";
import Link from "next/link";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "KTS",
  description: "KTS",
};

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const users = await serverFetchData("/users", "GET");

  return (
    <html lang="ru" className="dark">
      <head>
        <ColorSchemeScript />
      </head>
      <body className={cn(inter.className, "bg-background")}>
        <MantineProvider>
          <UserContextProvider isLoggedIn={!!users}>
            <div>
              <header className="text-text text-2xl p-2 border-b border-secondary flex justify-between sticky top-0 z-10 bg-background">
                <div className="flex items-center gap-4 text-text">
                  <Link href="/">
                    <span>KTS</span>
                  </Link>
                  <nav>
                    <Link href="/">
                      <Button className="hover:underline text-text bg-background">
                        Главная
                      </Button>
                    </Link>
                    <Link href="/account">
                      <Button className="hover:underline text-text bg-background">
                        Профиль
                      </Button>
                    </Link>
                  </nav>
                </div>
                <LoginButtons />
              </header>
              <main>{children}</main>
            </div>
          </UserContextProvider>
        </MantineProvider>
      </body>
    </html>
  );
}
