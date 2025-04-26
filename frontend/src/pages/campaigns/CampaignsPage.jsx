import { useEffect, useState, useRef } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import CampaignCard from "../../components/CampaignCard";

export default function CampaignsPage() {
  const [campaigns, setCampaigns] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState("");
  const [hasMore, setHasMore] = useState(true);
  const loaderRef = useRef(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadCampaigns();
  }, [page]);

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && hasMore) setPage((prev) => prev + 1);
      },
      { threshold: 1.0 }
    );
    if (loaderRef.current) observer.observe(loaderRef.current);
    return () => loaderRef.current && observer.unobserve(loaderRef.current);
  }, [loaderRef, hasMore]);

  useEffect(() => {
    const lower = search.toLowerCase();
    setFiltered(
      campaigns.filter(
        (c, i, self) =>
          (c.name.toLowerCase().includes(lower) ||
            c.slug.toLowerCase().includes(lower)) &&
          self.findIndex((x) => x.id === c.id) === i
      )
    );
  }, [search, campaigns]);

  const loadCampaigns = async () => {
    try {
      const res = await axios.get(`http://localhost:8081/api/campaigns?page=${page}`);
      if (res.data.length === 0) setHasMore(false);
      setCampaigns((prev) => {
        const ids = new Set(prev.map((c) => c.id));
        const unique = res.data.filter((c) => !ids.has(c.id));
        return [...prev, ...unique];
      });
    } catch (err) {
      toast.error("Error al cargar campañas");
    }
  };

  const statusColor = (status) => {
    switch (status) {
      case "ACTIVA": return "border-green-400";
      case "INACTIVA": return "border-yellow-400";
      case "FINALIZADA": return "border-red-400";
      default: return "border-gray-400";
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-900 to-gray-800 text-white flex flex-col">
      <main className="flex-1 container mx-auto px-4 py-10">
        <div className="sticky top-4 z-20 bg-gray-900 p-4 rounded-xl shadow-lg flex flex-col md:flex-row items-center gap-4">
          <input
            type="text"
            className="w-full md:flex-1 px-4 py-3 rounded-lg border border-gray-600 bg-gray-900 text-white placeholder-gray-400 shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
            placeholder="Buscar campaña por nombre o slug..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button
            onClick={() => navigate("/campaigns/new")}
            className="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-3 rounded-lg font-semibold text-sm shadow-md transition border border-indigo-400"
          >
            ＋ Nueva Campaña
          </button>
        </div>

        {filtered.length === 0 && (
          <div className="text-center text-gray-300 mt-10">No se encontraron campañas.</div>
        )}

        <div className="grid gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 mt-10">
          {filtered.map(c => (
            <CampaignCard key={c.id} campaign={c} />
          ))}
        </div>

        <div ref={loaderRef} className="py-8 text-center text-gray-400 text-sm">
          {hasMore ? "Cargando más campañas..." : "Has llegado al final."}
        </div>
      </main>
    </div>
  );
}