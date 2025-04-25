import { useEffect, useState } from "react";
import axios from "axios";
import CampaignPreview from "../../components/CampaignPreview";
import { toast } from "react-toastify";
import { FaCheckCircle, FaExclamationTriangle } from "react-icons/fa";
import "react-toastify/dist/ReactToastify.css";

const initialCampaignData = {
  name: "",
  slug: "",
  shortDescription: "",
  longDescription: "",
  gameId: "",
  gameDescription: "",
  dmId: "",
  startDate: new Date().toISOString().split("T")[0],
};

export default function CampaignsNewPage() {
  const [campaignData, setCampaignData] = useState(
    JSON.parse(localStorage.getItem("campaignDraft")) || initialCampaignData
  );
  const [games, setGames] = useState([]);
  const [players, setPlayers] = useState([]);
  const [creatingGame, setCreatingGame] = useState(false);
  const [creatingPlayer, setCreatingPlayer] = useState(false);
  const [newGame, setNewGame] = useState({ name: "", description: "" });
  const [newPlayer, setNewPlayer] = useState({ realName: "", displayName: "" });
  const [restoredDraft, setRestoredDraft] = useState(false);
  const [loading, setLoading] = useState(false);
  const [slugError, setSlugError] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8081/api/games").then((res) => setGames(res.data));
    axios.get("http://localhost:8081/api/players").then((res) => setPlayers(res.data));

    const draft = localStorage.getItem("campaignDraft");
    if (draft) {
      setRestoredDraft(true);
      toast.info("Se ha restaurado un borrador anterior. Puedes descartarlo si lo deseas.");
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const updated = { ...campaignData, [name]: value };
    setCampaignData(updated);
    localStorage.setItem("campaignDraft", JSON.stringify(updated));
    if (name === "slug") validateSlug(value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (slugError) {
      toast.error("Por favor, corrige los errores antes de enviar.");
      return;
    }
    setLoading(true);
    try {
      const payload = {
        ...campaignData,
        status: undefined,
      };
      await axios.post("http://localhost:8081/api/campaigns", payload);
      toast.success("Campaña creada exitosamente");
      localStorage.removeItem("campaignDraft");
      setCampaignData(initialCampaignData);
    } catch (err) {
      if (err.response?.data?.errors) {
        Object.entries(err.response.data.errors).forEach(([field, messages]) => {
          messages.forEach((msg) => toast.error(`${field}: ${msg}`));
        });
      } else {
        toast.error("Error al crear campaña");
      }
    } finally {
      setLoading(false);
    }
  };

  const createGame = async () => {
    if (!newGame.name.trim()) return toast.error("El nombre del sistema es obligatorio");
    try {
      const res = await axios.post("http://localhost:8081/api/games", newGame);
      setGames([...games, res.data]);
      setCampaignData({ ...campaignData, gameId: res.data.id });
      setCreatingGame(false);
      setNewGame({ name: "", description: "" });
    } catch {
      toast.error("Error al crear juego/sistema");
    }
  };

  const createPlayer = async () => {
    if (!newPlayer.realName.trim() || !newPlayer.displayName.trim()) {
      return toast.error("Nombre real y nombre en pantalla son obligatorios");
    }
    try {
      const res = await axios.post("http://localhost:8081/api/players", newPlayer);
      setPlayers([...players, res.data]);
      setCampaignData({ ...campaignData, dmId: res.data.id });
      setCreatingPlayer(false);
      setNewPlayer({ realName: "", displayName: "" });
    } catch {
      toast.error("Error al crear DJ");
    }
  };

  const resetForm = () => {
    if (confirm("¿Seguro que quieres limpiar el formulario?")) {
      localStorage.removeItem("campaignDraft");
      setCampaignData(initialCampaignData);
    }
  };

  const validateSlug = async (slug) => {
    if (!slug.trim()) {
      setSlugError("El slug no puede estar vacío.");
      return;
    }
    try {
      const res = await axios.get(`http://localhost:8081/api/campaigns/validate-slug?slug=${slug}`);
      setSlugError(res.data.exists ? "El slug ya está en uso." : "");
    } catch {
      setSlugError("Error al validar el slug.");
    }
  };

  const discardDraft = () => {
    localStorage.removeItem("campaignDraft");
    setRestoredDraft(false);
    setCampaignData(initialCampaignData);
  };

  const fieldClass = (value, required = true) => {
    const isValid = value?.trim();
    const borderColor = required
      ? isValid
        ? "border-green-700"
        : "border-red-500"
      : isValid
      ? "border-green-700"
      : "border-amber-500";
    return `w-full bg-white text-black pl-8 border-l-8 ${borderColor} border-2 p-2 rounded shadow-sm transition-all duration-150 relative`;
  };

  const Icon = ({ valid }) => valid ? (
    <FaCheckCircle className="absolute left-2 top-1/2 transform -translate-y-1/2 text-green-700" />
  ) : (
    <FaExclamationTriangle className="absolute left-2 top-1/2 transform -translate-y-1/2 text-yellow-500" />
  );

  return (
    <div className="max-w-4xl mx-auto p-4 space-y-4">
      <h1 className="text-2xl font-bold">Nueva Campaña</h1>
      {restoredDraft && (
        <div className="bg-yellow-300 text-black p-2 rounded animate-fadeIn">
          Se ha restaurado un borrador anterior.
          <button onClick={discardDraft} className="ml-2 text-red-700 underline">
            Descartar
          </button>
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-3">
        <div className="relative">
          <Icon valid={!!campaignData.name?.trim()} />
          <input
            className={fieldClass(campaignData.name)}
            placeholder="Campaña: La Última Esperanza"
            name="name"
            value={campaignData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="relative">
          <Icon valid={!!campaignData.slug?.trim() && !slugError} />
          <input
            className={fieldClass(campaignData.slug)}
            placeholder="Slug (único, sin espacios)"
            name="slug"
            value={campaignData.slug}
            onChange={handleChange}
            required
          />
          {slugError && <p className="text-red-500 text-sm mt-1 ml-2">{slugError}</p>}
        </div>

        <div className="relative">
          <Icon valid={!!campaignData.shortDescription?.trim()} />
          <textarea
            className={fieldClass(campaignData.shortDescription)}
            placeholder="Descripción corta"
            name="shortDescription"
            value={campaignData.shortDescription}
            onChange={handleChange}
            required
          />
        </div>

        <div className="relative">
          <Icon valid={!!campaignData.longDescription?.trim()} />
          <textarea
            className={fieldClass(campaignData.longDescription, false)}
            placeholder="Narrativa completa"
            name="longDescription"
            value={campaignData.longDescription}
            onChange={handleChange}
          />
        </div>

        <div className="relative">
          <Icon valid={!!campaignData.startDate?.trim()} />
          <input
            className={fieldClass(campaignData.startDate)}
            type="date"
            name="startDate"
            value={campaignData.startDate}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label className="text-white">Juego o Sistema:</label>
          <select
            className={fieldClass(campaignData.gameId)}
            name="gameId"
            value={campaignData.gameId}
            onChange={handleChange}
            required
          >
            <option value="">Seleccionar...</option>
            {games.map((g) => (
              <option key={g.id} value={g.id}>
                {g.name}
              </option>
            ))}
          </select>
          <button type="button" className="text-blue-600 underline" onClick={() => setCreatingGame(!creatingGame)}>
            + Crear Juego/Sistema
          </button>
          {creatingGame && (
            <div className="mt-2 animate-fadeIn bg-white text-black p-4 rounded shadow-md">
              <input
                className={fieldClass(newGame.name)}
                placeholder="Nombre del Sistema"
                value={newGame.name}
                onChange={(e) => setNewGame({ ...newGame, name: e.target.value })}
              />
              <input
                className={fieldClass(newGame.description, false)}
                placeholder="Descripción del Sistema"
                value={newGame.description}
                onChange={(e) => setNewGame({ ...newGame, description: e.target.value })}
              />
              <button type="button" onClick={createGame} className="bg-blue-500 text-white px-4 py-1 rounded mt-2">
                Crear
              </button>
            </div>
          )}
        </div>

        <div>
          <label className="text-white">Director de Juego:</label>
          <select
            className={fieldClass(campaignData.dmId)}
            name="dmId"
            value={campaignData.dmId}
            onChange={handleChange}
            required
          >
            <option value="">Seleccionar...</option>
            {players
              .filter((p) => p.realName && p.displayName)
              .map((p) => (
                <option key={p.id} value={p.id}>
                  {p.displayName} ({p.realName})
                </option>
              ))}
          </select>
          <button type="button" className="text-blue-600 underline" onClick={() => setCreatingPlayer(!creatingPlayer)}>
            + Crear DJ
          </button>
          {creatingPlayer && (
            <div className="mt-2 animate-fadeIn bg-white text-black p-4 rounded shadow-md">
              <input
                className={fieldClass(newPlayer.realName)}
                placeholder="Nombre real"
                value={newPlayer.realName}
                onChange={(e) => setNewPlayer({ ...newPlayer, realName: e.target.value })}
              />
              <input
                className={fieldClass(newPlayer.displayName)}
                placeholder="Nombre en pantalla"
                value={newPlayer.displayName}
                onChange={(e) => setNewPlayer({ ...newPlayer, displayName: e.target.value })}
              />
              <button type="button" onClick={createPlayer} className="bg-blue-500 text-white px-4 py-1 rounded mt-2">
                Crear
              </button>
            </div>
          )}
        </div>

        <div className="flex items-stretch gap-2 mt-4">
          <button
            disabled={loading}
            className="bg-green-600 text-white text-lg px-4 py-3 rounded disabled:opacity-50 w-4/5 h-full"
            type="submit"
          >
            {loading ? "Guardando..." : "Crear Campaña"}
          </button>
          <button
            type="button"
            onClick={resetForm}
            className="bg-red-600 text-white text-lg px-4 py-3 rounded w-[15%] h-full"
          >
            Limpiar
          </button>
        </div>
      </form>

      <div className="mt-6">
        <CampaignPreview data={campaignData} />
      </div>
    </div>
  );
}
