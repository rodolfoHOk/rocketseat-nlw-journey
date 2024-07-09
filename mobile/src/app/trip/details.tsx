import { Text, View } from 'react-native';

export function Details({ tripId }: { tripId: string }) {
  return (
    <View className="flex-1 mt-5">
      <Text className="text-zinc-50 text-2xl font-semibold">
        Details: {tripId}
      </Text>
    </View>
  );
}
