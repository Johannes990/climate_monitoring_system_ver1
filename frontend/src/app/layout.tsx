import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "My App",
  description: "My Next.js Application",
};

export default function RootLayout({
                                     children,
                                   }: {
  children: React.ReactNode;
}) {
  return (
      <html lang="en">
      <body className={inter.className}>
      <nav className="p-4 bg-gray-800 text-white">
        <div className="container mx-auto">
          <a href="/" className="mr-4 hover:underline">
            Home
          </a>
          <a href="/login" className="mr-4 hover:underline">
            Login
          </a>
          <a href="/register" className="hover:underline">
            Register
          </a>
        </div>
      </nav>
      <main>{children}</main>
      </body>
      </html>
  );
}
