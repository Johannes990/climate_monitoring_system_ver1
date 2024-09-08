import  "../globals.css";
import React from "react";
import {DASHBOARD_URL_PATH} from "../utils/constants"

export default function LoginLayout({ children }: { children: React.ReactNode}) {
    return (
        <div>
            <nav>
                <div className="navbar">
                    <div className="navbar-container">
                        <>
                            <div className="nav-links">
                                <a href={DASHBOARD_URL_PATH} className="nav-link">Dashboard</a>
                            </div>
                        </>
                        <>
                            <div className="nav-button">
                                <button>Logout</button>
                            </div>
                        </>
                    </div>
                </div>
            </nav>
            <main>{children}</main>
        </div>
    );
}