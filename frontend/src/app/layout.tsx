import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Climate Monitoring",
  description: "Climate monitoring system application",
};

export default function RootLayout({
                                     children,
                                   }: {
  children: React.ReactNode;
}) {

    return (
        <html lang="en">
        <body className={inter.className}>
        <main>{children}</main>
        </body>
        </html>
    );
}
