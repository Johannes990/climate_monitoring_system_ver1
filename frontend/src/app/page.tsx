// app/page.tsx (or app/page.tsx if you want to redirect directly)
import { redirect } from 'next/navigation';

export default function HomePage() {
    redirect('/login/login');
    return null; // This will never be rendered, as the redirect will happen first
}
