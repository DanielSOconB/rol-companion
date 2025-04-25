import { useEffect, useState } from 'react'
    import axios from 'axios'
    import { useNavigate } from 'react-router-dom'

    export default function HomePage() {
      const [campaigns, setCampaigns] = useState([])
      const [search, setSearch] = useState('')
      const navigate = useNavigate()

      useEffect(() => {
        axios.get(`${import.meta.env.VITE_API_URL}/campaigns`)
          .then(res => setCampaigns(res.data))
          .catch(() => alert('Error al obtener campañas'))
      }, [])

      const filteredCampaigns = campaigns.filter(c =>
        c.name.toLowerCase().includes(search.toLowerCase()) ||
        c.slug.toLowerCase().includes(search.toLowerCase())
      )

      const statusColor = (status) => {
        switch (status) {
          case "ACTIVA": return "border-green-400";
          case "INACTIVA": return "border-yellow-400";
          case "FINALIZADA": return "border-red-400";
          default: return "border-gray-400";
        }
      }

      return (
        <div className="min-h-screen bg-gradient-to-b from-gray-900 to-gray-800 text-white flex flex-col">
          <main className="flex-1 container mx-auto px-4 py-10">
            <h1 className="text-4xl sm:text-5xl font-bold mb-10 text-center flex items-center justify-center gap-4">
              <span role="img" aria-label="dice">🎲</span> Mis Campañas de Rol
            </h1>

            <div className="mb-10 max-w-xl mx-auto">
              <input
                type="text"
                placeholder="Buscar campaña..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                className="w-full px-4 py-3 rounded-lg border border-gray-600 bg-gray-900 text-white placeholder-gray-400 shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>

            <div className="grid gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4">
              {filteredCampaigns.slice(0, 3).map(c => (
                <div
                  key={c.id}
                  onClick={() => navigate(`/campaigns/${c.slug}`)}
                  className={`bg-gray-800 group p-6 rounded-xl shadow-md hover:shadow-xl hover:bg-gray-700 transition-all cursor-pointer flex flex-col justify-between h-full border-l-8 ${statusColor(c.status)} animate-fadeIn`}
                >
                  <div className="flex items-center gap-3 mb-2">
                    <span className="text-3xl">📘</span>
                    <h2 className="text-xl font-semibold truncate">{c.name}</h2>
                  </div>
                  <p className="text-sm text-gray-400 mb-4 line-clamp-3 flex-grow">{c.shortDescription || 'Sin descripción'}</p>
                  <div className="text-right text-xs font-medium text-indigo-400">
                    {c.status === 'IN_PROGRESS' ? 'En curso' : c.status === 'INACTIVE' ? 'Inactiva' : c.status === 'FINISHED' ? 'Finalizada' : 'Pendiente'}
                  </div>
                </div>
              ))}

              <div
                onClick={() => navigate('/campaigns/new')}
                className="bg-indigo-600 hover:bg-indigo-500 flex flex-col items-center justify-center p-6 rounded-xl border-2 border-dashed border-indigo-400 hover:border-white cursor-pointer transition"
              >
                <span className="text-5xl">＋</span>
                <p className="mt-2 text-lg font-medium">Nueva Campaña</p>
              </div>
            </div>
          </main>

          <footer className="text-center text-sm text-gray-500 py-6 border-t border-gray-700">
            Mapa del sitio | Créditos | Contacto
          </footer>
        </div>
      )
    }