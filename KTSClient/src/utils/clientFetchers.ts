export const clientFetchData = async (
  url: string,
  method: string,
  body?: any
) => {
  const result = await fetch(url, {
    method,
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },
    body: body ? JSON.stringify(body) : undefined,
  });

  if (!result.ok) throw new Error((await result.json()).message);
  try {
    return await result.json();
  } catch (err) {
    return;
  }
};
