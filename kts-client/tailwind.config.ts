import type { Config } from "tailwindcss";

const config: Config = {
  darkMode: "class",
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
      colors: {
        text: {
          DEFAULT: "rgb(var(--text))",
        },
        background: {
          DEFAULT: "rgb(var(--background))",
        },
        primary: {
          DEFAULT: "rgb(var(--primary))",
        },
        secondary: {
          DEFAULT: "rgb(var(--secondary))",
        },
        accent: {
          DEFAULT: "rgb(var(--accent))",
        },
        success: {
          DEFAULT: "rgb(var(--success))",
        },
        danger: {
          DEFAULT: "rgb(var(--danger))",
        },
      },
    },
  },
  plugins: [],
};
export default config;
