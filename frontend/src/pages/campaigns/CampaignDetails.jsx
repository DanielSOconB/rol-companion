import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import CampaignPreview from "../../components/CampaignPreview";
import DeleteCampaignModal from "../../components/DeleteCampaignModal";

const statusTranslations = {
  PENDING: { text: "Pendiente", color: "text-yellow-400" },
  ACTIVE: { text: "Activa", color: "text-green-400" },
  FINISHED: { text: "Finalizada", color: "text-blue-400" },
  INACTIVE: { text: "Inactiva", color: "text-gray-400" },
  DISCARDED: { text: "Descartada", color: "text-red-400" },
};

export default function CampaignDetails() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const [campaign, setCampaign] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expanded, setExpanded] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => {
    fetchCampaign();
  }, [slug]);

  const fetchCampaign = async () => {
    try {
      const res = await axios.get(`http://localhost:8081/api/campaigns/slugs/${slug}`);
      setCampaign(res.data);
    } catch (err) {
      console.error(err);
      setError("No se pudo cargar la campaña");
      toast.error("No se pudo cargar la campaña");
    } finally {
      setLoading(false);
    }
  };

  const updateStatus = async (newStatus) => {
    try {
      await axios.patch(`http://localhost:8081/api/campaigns/${campaign.id}/status`, { status: newStatus });
      toast.success(`Estado actualizado a ${statusTranslations[newStatus]?.text || newStatus}`);
      fetchCampaign();
    } catch {
      toast.error("No se pudo actualizar el estado");
    }
  };

  const deleteCampaign = async () => {
    try {
      await axios.delete(`http://localhost:8081/api/campaigns/${campaign.id}`);
      toast.success("Campaña eliminada");
      navigate("/");
    } catch {
      toast.error("No se pudo eliminar la campaña");
    }
  };

  if (loading) {
    return <div className="text-center text-gray-400 animate-pulse">Cargando campaña...</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">{error}</div>;
  }

  const statusInfo = statusTranslations[campaign.status] || { text: campaign.status, color: "text-gray-300" };

  return (
    <div className="max-w-5xl mx-auto p-6 space-y-8">
      <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-4">
        <h1 className="text-4xl font-extrabold">{campaign.name}</h1>
        <span className={`text-md font-semibold ${statusInfo.color}`}>{statusInfo.text}</span>
      </div>

      <div className="animate-fadeIn">
        <div className="bg-gray-800 group p-6 rounded-xl shadow-md hover:shadow-xl hover:bg-gray-700 transition-all cursor-pointer flex flex-col gap-3 border-l-8 border-indigo-500">
          <div className="flex items-center gap-3">
            <span className="text-3xl">📘</span>
            <h2 className="text-2xl font-bold">{campaign.name}</h2>
          </div>
          <p className="text-sm text-gray-400">
            {campaign.shortDescription || "Descripción corta de la campaña"}
          </p>
          <div className="text-right text-xs font-medium text-indigo-400">
            {statusInfo.text}
          </div>
        </div>
      </div>

      <div className="flex flex-wrap gap-4">
        <button
          onClick={() => updateStatus("ACTIVE")}
          className="bg-green-600 hover:bg-green-700 text-white font-bold px-8 py-4 rounded-xl flex-1"
        >
          🎲 Jugar
        </button>
        <button
          onClick={() => updateStatus("FINISHED")}
          className="bg-blue-600 hover:bg-blue-700 text-white font-bold px-8 py-4 rounded-xl flex-1"
        >
          ✅ Finalizar
        </button>
        <button
          onClick={() => updateStatus("DISCARDED")}
          className="bg-yellow-500 hover:bg-yellow-600 text-white font-bold px-8 py-4 rounded-xl flex-1"
        >
          📁 Archivar
        </button>
        <button
          onClick={() => setShowDeleteModal(true)}
          className="bg-red-600 hover:bg-red-700 text-white font-bold px-8 py-4 rounded-xl flex-1"
        >
          🗑️ Borrar
        </button>
      </div>

      <div className="bg-gray-800 p-6 rounded-xl shadow-lg text-white space-y-4">
        <p><strong>Slug:</strong> <code className="text-indigo-400">{campaign.slug}</code></p>
        <p><strong>Inicio:</strong> {campaign.startDate || "Sin definir"}</p>
        <p><strong>Juego:</strong> {campaign.system ? (
          <Link to={`/systems/${campaign.system.slug}`} className="text-indigo-400 hover:underline">
            {campaign.system.name}
          </Link>
        ) : "No especificado"}</p>
        <p><strong>DJ:</strong> {campaign.gameMaster ? (
          <Link to={`/users/${campaign.gameMaster.id}`} className="text-indigo-400 hover:underline">
            {campaign.gameMaster.displayName || campaign.gameMaster.realName}
          </Link>
        ) : "No especificado"}</p>

        <div className="transition-all">
          <strong>Descripción larga:</strong>
          <p className="mt-1 whitespace-pre-line">
            {expanded
              ? (campaign.longDescription || "No disponible")
              : (campaign.longDescription?.slice(0, 300) + (campaign.longDescription?.length > 300 ? "..." : "") || "No disponible")}
          </p>
          {campaign.longDescription?.length > 300 && (
            <button
              onClick={() => setExpanded(!expanded)}
              className="mt-2 text-indigo-400 hover:underline"
            >
              {expanded ? "Ver menos" : "Ver más"}
            </button>
          )}
        </div>
      </div>

      {showDeleteModal && (
        <DeleteCampaignModal
          isOpen={showDeleteModal}
          onClose={() => setShowDeleteModal(false)}
          onConfirm={deleteCampaign}
        />
      )}
    </div>
  );
}
