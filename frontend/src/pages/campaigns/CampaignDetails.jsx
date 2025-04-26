import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import CampaignPreview from "../../components/CampaignPreview";
import DeleteCampaignModal from "../../components/DeleteCampaignModal";

export default function CampaignDetails() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const [campaign, setCampaign] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expanded, setExpanded] = useState(false);

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
      toast.success(`Estado actualizado a ${newStatus}`);
      fetchCampaign();
    } catch {
      toast.error("No se pudo actualizar el estado");
    }
  };

  const [showDeleteModal, setShowDeleteModal] = useState(false);

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

  return (
    <div className="max-w-4xl mx-auto p-4 space-y-6">
      <h1 className="text-3xl font-bold">{campaign.name}</h1>

      <div className="animate-fadeIn">
        <CampaignPreview data={campaign} />
      </div>

      <div className="flex flex-wrap gap-4 mt-6">
        <button
          onClick={() => updateStatus("ACTIVE")}
          className="bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-lg flex-1"
        >
          Iniciar Sesión
        </button>

        <button
          onClick={() => updateStatus("FINISHED")}
          className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg flex-1"
        >
          Finalizar Campaña
        </button>

        <button
          onClick={() => updateStatus("DISCARDED")}
          className="bg-yellow-500 hover:bg-yellow-600 text-white px-6 py-3 rounded-lg flex-1"
        >
          Archivar Campaña
        </button>

        <button
          onClick={() => setShowDeleteModal(true)}
          className="bg-red-600 hover:bg-red-700 text-white px-6 py-3 rounded-lg flex-1"
        >
          🗑️ Eliminar Campaña
        </button>

        <DeleteCampaignModal
          isOpen={showDeleteModal}
          onClose={() => setShowDeleteModal(false)}
          onConfirm={deleteCampaign}
        />

      </div>

      <div className="bg-gray-800 p-6 rounded-lg shadow text-white space-y-4">
        <p><strong>Slug:</strong> {campaign.slug}</p>
        <p><strong>Estado:</strong> {campaign.status}</p>
        <p><strong>Inicio:</strong> {campaign.startDate}</p>
        <p><strong>Juego:</strong> {campaign.system?.name || "No especificado"}</p>
        <p><strong>DJ:</strong> {campaign.gameMaster?.displayName || campaign.gameMaster?.realName || "No especificado"}</p>

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
    </div>
  );
}
