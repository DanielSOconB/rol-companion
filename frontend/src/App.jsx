import { useEffect, useState } from 'react'
import axios from 'axios'

export default function App() {
  const [campaigns, setCampaigns] = useState([])
  const [form, setForm] = useState({ name: '', slug: '', status: 'ACTIVE' })

  useEffect(() => {
    fetchCampaigns()
  }, [])

  const fetchCampaigns = () => {
    axios.get(`${import.meta.env.VITE_API_URL}/campaigns`)
      .then(res => setCampaigns(res.data))
      .catch(() => alert('Error al obtener campañas'))
  }

  const createCampaign = (e) => {
    e.preventDefault()
    axios.post(`${import.meta.env.VITE_API_URL}/campaigns`, form)
      .then(() => {
        fetchCampaigns()
        setForm({ name: '', slug: '', status: 'ACTIVE' })
      })
      .catch(() => alert('Error al crear campaña'))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-300 p-6">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-4xl font-bold mb-8 text-center">🎲 Mis Campañas de Rol</h1>

        <form onSubmit={createCampaign} className="mb-10 bg-white p-6 rounded-2xl shadow-md space-y-4">
          <div className="flex flex-col gap-2">
            <input
              type="text"
              placeholder="Nombre de la campaña"
              className="p-3 border rounded-lg"
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Slug único"
              className="p-3 border rounded-lg"
              value={form.slug}
              onChange={(e) => setForm({ ...form, slug: e.target.value })}
              required
            />
            <select
              className="p-3 border rounded-lg"
              value={form.status}
              onChange={(e) => setForm({ ...form, status: e.target.value })}
            >
              <option value="ACTIVE">Activa</option>
              <option value="INACTIVE">Inactiva</option>
              <option value="PLANNED">Por empezar</option>
              <option value="ARCHIVED">Archivada</option>
            </select>
            <button type="submit" className="mt-2 bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg">
              Crear campaña
            </button>
          </div>
        </form>

        <section className="grid sm:grid-cols-2 gap-6">
          {campaigns.map((c) => (
            <div key={c.id} className="bg-white p-5 rounded-xl shadow-md">
              <h2 className="text-2xl font-bold text-gray-800">{c.name}</h2>
              <p className="text-sm text-gray-500">Estado: {c.status}</p>
            </div>
          ))}
        </section>
      </div>
    </div>
  )
}
