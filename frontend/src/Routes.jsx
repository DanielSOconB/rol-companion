import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import MainLayout from './layout/MainLayout'
import HomePage from './pages/HomePage'
import CampaignsPage from './pages/campaigns/CampaignsPage'
import CharactersPage from './pages/CharactersPage'
import GamesPage from './pages/GamesPage'
import TemplatesPage from './pages/TemplatesPage'
import NotesPage from './pages/NotesPage'
import UsersPage from './pages/UsersPage'
import SessionsPage from './pages/SessionsPage'
import CampaignsNewPage from './pages/campaigns/CampaignsNewPage'
import CampaignDetails from './pages/campaigns/CampaignDetails'


export default function AppRouter() {
  return (
    <Router>
      <Routes>
        <Route element={<MainLayout />}>
          <Route index element={<HomePage />} />
          <Route path="/campaigns" element={<CampaignsPage />} />
          <Route path="/campaigns/new" element={<CampaignsNewPage />} />
          <Route path="/characters" element={<CharactersPage />} />
          <Route path="/games" element={<GamesPage />} />
          <Route path="/templates" element={<TemplatesPage />} />
          <Route path="/notes" element={<NotesPage />} />
          <Route path="/users" element={<UsersPage />} />
          <Route path="/sessions" element={<SessionsPage />} />
          <Route path="/campaigns/slugs/:slug" element={<CampaignDetails />} />
        </Route>
      </Routes>
    </Router>
  )
}
