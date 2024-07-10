# NLW Journey - Mobile

## Tecnologias

- Typescript / Javascript
- Expo / React Native
- NativeWind / Tailwind CSS

### Bibliotecas adicionais

- expo-font
- expo-google-fonts/inter
- clsx
- react-native-svg
- lucide-react-native
- react-native-calendars
- expo-blur
- axios
- zod
- react-native-async-storage/async-storage

## Rodar

### Requisitos

- Node.js 20

### Comandos

#### Rodar o servidor

- entrar na pasta nodejs
- renomear .env.example para .env
- comando: npm install
- comando: npm run dev

#### Rodar o aplicativo

- entrar na pasta mobile
- comando: npm install
- comando: npx expo start
- no dispositivo físico: instalar o app Expo Go
- no dispositivo físico: estando na mesma rede do PC
- no dispositivo físico: escanear o QRCode que aparece

## Teste do app link

A. Por terminal

- npx expo prebuild
- npx expo run:android
- npx uri-scheme open "planner://trip/c305eea0-1388-409a-8f15-debea3e21d2f?participant=2f4e0c72-cf4e-45cb-86ba-d0954bbcb351" --android

B. Por simulador de e-mail

- obs: a viagem deve estar confirmada
- abrir no vscode com a extensão rest client instalada: api.http
- clicar: send request
- no log do servidor nodejs terá um link, copie o link
- no navegador do dispositivo android, cole o link
- vai abrir uma página de simulação de e-mail: clicar em Confirmar viagem no corpo do e-mail
