import { Dialog } from "@headlessui/react";

export default function DeleteCampaignModal({ isOpen, onClose, onConfirm }) {
  if (!isOpen) return null;

  return (
    <Dialog open={isOpen} onClose={onClose} className="fixed inset-0 flex items-center justify-center z-50">
      <div className="fixed inset-0 bg-black bg-opacity-50"></div>

      <div className="bg-white rounded-lg shadow-xl p-8 z-10 max-w-md mx-auto">
        <Dialog.Title className="text-xl font-bold mb-4 text-gray-800">¿Eliminar campaña?</Dialog.Title>
        <Dialog.Description className="text-gray-600 mb-6">
          Esta acción no se puede deshacer. ¿Estás seguro de que quieres eliminar esta campaña?
        </Dialog.Description>

        <div className="flex justify-end gap-4">
          <button
            onClick={onClose}
            className="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded text-gray-800"
          >
            Cancelar
          </button>
          <button
            onClick={onConfirm}
            className="px-4 py-2 bg-red-600 hover:bg-red-700 rounded text-white"
          >
            Confirmar
          </button>
        </div>
      </div>
    </Dialog>
  );
}
