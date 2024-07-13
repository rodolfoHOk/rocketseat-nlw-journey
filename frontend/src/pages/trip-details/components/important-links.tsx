import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link2, Plus } from 'lucide-react';
import { Button } from '../../../components/button';
import { Loading } from '../../../components/loading';
import { api } from '../../../lib/axios';

interface Link {
  id: string;
  title: string;
  url: string;
}

interface ImportantLinksProps {
  openCreateLinkModal: () => void;
  showAlert: (message: string) => void;
}

export function ImportantLinks({
  openCreateLinkModal,
  showAlert,
}: ImportantLinksProps) {
  const { tripId } = useParams();
  const [links, setLinks] = useState<Link[]>([]);

  const [isLoadingData, setIsLoadingData] = useState(true);

  async function fetchData() {
    try {
      const response = await api.get(`trips/${tripId}/links`);
      setLinks(response.data.links);
      setIsLoadingData(false);
    } catch (error) {
      showAlert('Erro ao tentar buscar dados de links da viagem');
      setIsLoadingData(false);
    }
  }

  useEffect(() => {
    fetchData();
  }, [tripId]);

  return (
    <div className="space-y-6">
      <h2 className="font-semibold text-xl">Links importantes</h2>

      {isLoadingData && <Loading color="secondary" size="lg" />}

      <div className="space-y-5">
        {links.map((link) => (
          <div
            key={link.id}
            className="flex items-center justify-between gap-4"
          >
            <div className="space-y-1.5">
              <span className="block font-medium text-zinc-100">
                {link.title}
              </span>

              <a
                href="#"
                className="block text-xs text-zinc-400 truncate hover:text-zinc-200 transition-colors duration-200"
              >
                {link.url}
              </a>
            </div>

            <Link2 className="text-zinc-400 size-5 shrink-0" />
          </div>
        ))}
      </div>

      <Button onClick={openCreateLinkModal} variant="secondary" size="full">
        <Plus className="size-5" />

        <span>Cadastrar novo link</span>
      </Button>
    </div>
  );
}
