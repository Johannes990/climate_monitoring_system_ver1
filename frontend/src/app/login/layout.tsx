import  "../globals.css";
import React from "react";
import {LOGIN_URL_PATH, REGISTER_URL_PATH} from "../utils/constants"

export default function LoginLayout({ children }: { children: React.ReactNode}) {
    return (
        <div>
            <nav>
            <div className="navbar">
                <div className="navbar-container">
                    <div className="nav-links">
                        <a href={LOGIN_URL_PATH} className="nav-link">Login</a>
                        <a href={REGISTER_URL_PATH} className="nav-link">Register</a>
                    </div>
                </div>
            </div>
            </nav>
        <main>{children}</main>
        </div>
    );
}