import { Text, View } from 'react-native';
import { TripData } from './[id]';

type Props = {
  tripDetails: TripData;
};

export function Activities({ tripDetails }: Props) {
  return (
    <View className="flex-1 mt-5">
      <Text className="text-zinc-50 text-2xl font-semibold">
        Atividades: {tripDetails.destination}
      </Text>
    </View>
  );
}
