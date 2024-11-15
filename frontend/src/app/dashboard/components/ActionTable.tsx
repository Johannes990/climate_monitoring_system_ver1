import {ActionDTO} from "@/app/dto/notification/ActionDTO";

type ActionTableProps = {
    actions: ActionDTO[];
};

const ActionTable: React.FC<ActionTableProps> =({ actions }) => {
    return (
        <div className="overflow-x-auto w-full">
            <table className="min-w-full bg-white border">
                <thead>
                    <tr>
                        <th className="px-3 py-2 border">Action ID</th>
                        <th className="px-3 py-2 border">Message</th>
                        <th className="px-3 py-2 border">User</th>
                        <th className="px-3 py-2 border">Time Taken</th>
                    </tr>
                </thead>
                <tbody>
                    {actions.map((action) => (
                        <tr key={action.actionId}>
                            <td className="px-3 py-2 border">{action.actionId}</td>
                            <td className="px-3 py-2 border">{action.message}</td>
                            <td className="px-3 py-2 border">{action.user.firstName} {action.user.lastName}</td>
                            <td className="px-3 py-2 border">
                                {action.timestamp?.toLocaleDateString('en-GB', {
                                    day: '2-digit',
                                    month: 'long',
                                    year: 'numeric'
                                })}, {action.timestamp?.toLocaleTimeString('en-GB', {
                                    hour: '2-digit',
                                    minute: '2-digit',
                                    second: '2-digit',
                                    hour12: false
                                })}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ActionTable;