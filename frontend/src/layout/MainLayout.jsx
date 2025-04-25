import { Link, Outlet } from "react-router-dom"
import { useState } from "react"

export default function MainLayout() {
  const [open, setOpen] = useState(false)

  return (
    <div className="flex h-screen bg-gray-900 text-white">
      <button
        onClick={() => setOpen(!open)}
        className="absolute top-4 left-4 z-50 md:hidden bg-gray-700 p-2 rounded"
      >
        ☰
      </button>

      <aside
        className={`fixed z-40 md:static transition-all duration-300 bg-gray-800 p-4 flex flex-col justify-between h-full w-64 ${
          open ? "left-0" : "-left-64"
        } md:left-0`}
      >
        <nav className="space-y-4">
          <Link to="/" className="block text-lg font-semibold hover:text-pink-400">Inicio</Link>
          <Link to="/campaigns" className="block text-lg font-semibold hover:text-pink-400">Campañas</Link>
          <Link to="/characters" className="block text-lg font-semibold hover:text-pink-400">Personajes</Link>
          <Link to="/games" className="block text-lg font-semibold hover:text-pink-400">Juegos</Link>
          <Link to="/templates" className="block text-lg font-semibold hover:text-pink-400">Plantillas</Link>
          <Link to="/notes" className="block text-lg font-semibold hover:text-pink-400">Notas</Link>
          <Link to="/users" className="block text-lg font-semibold hover:text-pink-400">Usuarios</Link>
          <Link to="/sessions" className="block text-lg font-semibold hover:text-pink-400">Sesiones</Link>
        </nav>
        <footer className="text-sm text-gray-400 mt-4">© 2025 Rol Companion</footer>
      </aside>

      <main className="flex-1 p-6 overflow-auto">
        <Outlet />
      </main>
    </div>
  )
}
