import { ImportTest } from "@/app/_components/ImportTest";
import { StartTest } from "@/app/_components/StartTest";

export default function Home() {
  return (
    <main className="flex flex-col items-center justify-between p-24 gap-4">
      <div className="flex flex-col gap-3">
        <StartTest />
        <ImportTest />
      </div>
    </main>
  );
}
