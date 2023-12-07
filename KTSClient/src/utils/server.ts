import { cookies } from "next/headers";

export const serverFetchData = async <T>(
  url: string,
  method: string
): Promise<T | undefined> => {
  const result = await fetch(`${process.env.API_URL}${url}`, {
    method,
    headers: {
      "Content-Type": "application/json",
      Cookie: decodeURIComponent(cookies().toString()),
    },
  });
  const json = await result.json();
  if (!result.ok) {
    console.error(json.message);
    return;
  }
  return json;
};
