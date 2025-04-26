import { useNavigate } from "react-router-dom";

const statusMap = {
  IN_PROGRESS: { label: "En curso", color: "border-green-400" },
  ACTIVE: { label: "Activa", color: "border-green-400" },
  INACTIVE: { label: "Inactiva", color: "border-yellow-400" },
  FINISHED: { label: "Finalizada", color: "border-blue-400" },
  DISCARDED: { label: "Descartada", color: "border-red-400" },
  PENDING: { label: "Pendiente", color: "border-gray-400" },
};

export default function CampaignCard({ campaign }) {
  const navigate = useNavigate();
  const { id, name, slug, shortDescription, status } = campaign;
  const info = statusMap[status] || { label: status, color: "border-gray-400" };

  return (
    <div
      onClick={() => navigate(`/campaigns/slugs/${slug}`)}
      className={`bg-gray-800 group p-6 rounded-xl shadow-md hover:shadow-xl hover:bg-gray-700 transition-all cursor-pointer flex flex-col justify-between h-full border-l-8 ${info.color} animate-fadeIn`}
    >
      <div className="flex items-center gap-3 mb-2">
        <span className="text-3xl">📘</span>
        <h2 className="text-xl font-semibold truncate">{name}</h2>
      </div>
      <p className="text-sm text-gray-400 mb-4 line-clamp-3 flex-grow">{shortDescription || 'Sin descripción'}</p>
      <div className="text-right text-xs font-medium text-indigo-400">{info.label}</div>
    </div>
  );
}
