export default function CampaignPreview({ data }) {
  return (
    <div className="border rounded p-4 shadow bg-white mt-4">
      <h2 className="text-xl font-bold">{data.name || "Sin nombre"}</h2>
      <p className="text-gray-500">{data.shortDescription || "Sin descripción"}</p>
      <p className="text-sm mt-2">Slug: <code>{data.slug}</code></p>
      <p className="text-sm">Inicio: {data.startDate || "No definida"}</p>
    </div>
  )
}
