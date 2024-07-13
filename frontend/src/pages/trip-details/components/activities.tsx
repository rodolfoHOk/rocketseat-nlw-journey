import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { CircleCheck, CircleDashed, Plus } from 'lucide-react';
import { format, isBefore } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Button } from '../../../components/button';
import { api } from '../../../lib/axios';
import { Loading } from '../../../components/loading';

interface Activity {
  date: string;
  activities: {
    id: string;
    title: string;
    occurs_at: string;
  }[];
}

interface ActivitiesProps {
  openCreateActivityModal: () => void;
  showAlert: (message: string) => void;
}

export function Activities({
  openCreateActivityModal,
  showAlert,
}: ActivitiesProps) {
  const { tripId } = useParams();
  const [activities, setActivities] = useState<Activity[]>([]);

  const [isLoadingData, setIsLoadingData] = useState(true);

  async function fetchData() {
    try {
      const response = await api.get(`trips/${tripId}/activities`);
      setActivities(response.data.activities);
      setIsLoadingData(false);
    } catch (error) {
      showAlert('Erro ao tentar buscar dados de atividades da viagem');
      setIsLoadingData(false);
    }
  }

  useEffect(() => {
    fetchData();
  }, [tripId]);

  return (
    <div className="flex-1 space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-semibold">Atividades</h2>

        <Button variant="primary" onClick={openCreateActivityModal}>
          <Plus className="size-5" />

          <span>Cadastrar atividade</span>
        </Button>
      </div>

      {isLoadingData && <Loading color="secondary" size="lg" />}

      <div className="space-y-8">
        {activities.map((day) => (
          <div key={day.date} className="space-y-2.5">
            <div className="flex gap-2 items-baseline">
              <span className="text-xl text-zinc-300 font-semibold">
                Dia {format(day.date, 'd')}
              </span>

              <span className="text-xs text-zinc-500">
                {format(day.date, 'EEEE', { locale: ptBR })}
              </span>
            </div>

            {day.activities.length > 0 ? (
              <div className="space-y-3">
                {day.activities.map((activity) => {
                  return (
                    <div key={activity.id} className="space-y-2.5">
                      <div className="px-4 py-2.5 bg-zinc-900 rounded-xl shadow-shape flex items-center gap-3">
                        {isBefore(
                          activity.occurs_at,
                          new Date() // not for test
                          // new Date(2024, 6, 18, 10, 0, 0) // for test
                        ) ? (
                          <CircleCheck className="size-5 text-lime-300" />
                        ) : (
                          <CircleDashed className="size-5 text-zinc-400" />
                        )}

                        <span className="text-zinc-100">{activity.title}</span>

                        <span className="text-zinc-400 text-sm ml-auto">
                          {format(activity.occurs_at, 'HH:mm')}h
                        </span>
                      </div>
                    </div>
                  );
                })}
              </div>
            ) : (
              <p className="text-zinc-500 text-sm">
                Nenhuma atividade cadastrada nessa data.
              </p>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
