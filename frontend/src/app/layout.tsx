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
        <nav className="p-4 bg-gray-800 text-white">
            <div className="navbar">
                <div className="navbar-container">
                    <div className="nav-links">
                        <a href="/login" className="nav-link">Login</a>
                        <a href="/register" className="nav-link">Register</a>
                        <a href="/dashboard" className="nav-link">Dashboard</a>
                    </div>
                    <div>
                        <button className="nav-button">Logout</button>
                    </div>
                </div>
            </div>
        </nav>
        <main>{children}</main>
        </body>
        </html>
    );
}
